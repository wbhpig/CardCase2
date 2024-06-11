package com.team4.cardcase2.foreground

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.team4.cardcase2.R
import com.team4.cardcase2.entity.Encoder
import com.team4.cardcase2.entity.ServerCard
import com.team4.cardcase2.entity.WholeServerCard
import com.team4.cardcase2.interfaces.HttpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val PICK_IMAGE_REQUEST = 1

class ScanFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 直接启动图库选择
        selectImageFromGallery()
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                val inputStream: InputStream? = context?.contentResolver?.openInputStream(selectedImageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                scanQRCodeFromBitmap(bitmap)
            }
        }
    }

    private fun scanQRCodeFromBitmap(bitmap: Bitmap) {
        val intArray = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val source = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
        val reader = MultiFormatReader()
        try {
            val result = reader.decode(binaryBitmap)
            getQRCode(result.text)
//            Toast.makeText(context, "Scanned: ${result.text}", Toast.LENGTH_LONG).show()
            // 使用接口回调传递结果
            (activity as? QRCodeScanResultListener)?.onQRCodeScanned(result.text)
        } catch (e: Exception) {
            Toast.makeText(context, "QR Code not found in image", Toast.LENGTH_LONG).show()
        }
    }

    private fun getQRCode(result: String){
        val coder: Encoder = Encoder()
        var qrResult = coder.decode(result)
//        Toast.makeText(context, "QR Code Result: $qrResult", Toast.LENGTH_LONG).show()
        val authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6IjE5NzIzNTY5NThAcXEuY29tIiwiZXhwIjoxNzE4NTIwMjQyfQ.mXJxuhtmxTx3_yvKv1MOgnGEccK7h_f21ugT-bvuViI"

        qrResult = 48
        val url = "https://578q07p228.vicp.fun/api/cards/${qrResult}"
        val httpRequest = HttpRequest()
        httpRequest.sendGetRequest(url, authToken) { response, exception ->
            if (exception != null) {
                // 请求失败，显示错误消息
                activity?.runOnUiThread {
//                    Toast.makeText(context, "Request failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                // 请求成功，显示响应消息
                activity?.runOnUiThread {
//                    Toast.makeText(context, "Response: $response", Toast.LENGTH_SHORT).show()
                    val wholeServerCard: WholeServerCard = WholeServerCard.fromJson(response!!)
                    val cardId: Int = wholeServerCard.card.cardId
//                    println(response)
//                    println(wholeServerCard)
//                    Toast.makeText(context, "Response: $wholeServerCard", Toast.LENGTH_SHORT).show()
                    showSingleChoiceDialog(cardId)
                    checkData(cardId)
                }
            }
        }

    }

    private fun showSingleChoiceDialog(cid:Int) {
        // 选项数组
        val options = getGids(1).toTypedArray()
        var selectedOption = 0 // 默认选中的选项

        // 构建弹窗
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose an option")
            .setSingleChoiceItems(options, selectedOption) { dialog, which ->
                selectedOption = which
            }
            .setPositiveButton("OK") { dialog, which ->
                // 用户点击确定按钮后的操作
                val chosenOption = options[selectedOption]
                Toast.makeText(requireContext(), "You selected: $chosenOption", Toast.LENGTH_LONG).show()
                addData(1, cid, chosenOption)
                // 你可以在这里处理用户选择的选项
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // 用户点击取消按钮后的操作
                dialog.dismiss()
            }

        // 显示弹窗
        val dialog = builder.create()
        dialog.show()
    }

    private fun getGids(uid: Int): List<String> {
        val db: SQLiteDatabase = requireContext().openOrCreateDatabase("sqlite.db", Context.MODE_PRIVATE, null)
        val gidList = mutableListOf<String>()

        val cursor: Cursor = db.rawQuery("SELECT DISTINCT gid FROM SQLTable WHERE uid = ?", arrayOf(uid.toString()))

        if (cursor.moveToFirst()) {
            do {
                val gid = cursor.getString(cursor.getColumnIndexOrThrow("gid"))
                gidList.add(gid)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return gidList
    }

    private fun addData(uid: Int, cid: Int, gid: String){
        val db: SQLiteDatabase = requireContext().openOrCreateDatabase("sqlite.db", Context.MODE_PRIVATE, null)
        // 检查是否存在相同的 uid 和 cid 的行
        val cursor = db.rawQuery("SELECT * FROM SQLTable WHERE uid = ? AND cid = ?", arrayOf(uid.toString(), cid.toString()))
        if (cursor.moveToFirst()) {
            // 行已存在，执行更新操作
            db.execSQL("UPDATE SQLTable SET gid = ? WHERE uid = ? AND cid = ?", arrayOf(gid, uid, cid))
        } else {
            // 行不存在，执行插入操作
            db.execSQL("INSERT INTO SQLTable (uid, cid, gid) VALUES (?, ?, ?)", arrayOf(uid, cid, gid))
        }
        // 关闭 Cursor 和数据库
        cursor.close()
        db.close()
        // 关闭数据库
        db.close()
    }

    private fun checkData(cid: Int){
        val db: SQLiteDatabase = requireContext().openOrCreateDatabase("sqlite.db", Context.MODE_PRIVATE, null)
        val cursor: Cursor = db.query(
            "SQLTable",       // 表名
            null,             // 列名，null 表示选择所有列
            "cid = ?",        // WHERE 子句
            arrayOf(cid.toString()), // WHERE 子句的参数
            null,             // GROUP BY 子句
            null,             // HAVING 子句
            null              // ORDER BY 子句
        )

        // 检查是否有数据并输出第一行
        if (cursor.moveToFirst()) {
            val uid = cursor.getInt(cursor.getColumnIndexOrThrow("uid"))
            val gid = cursor.getString(cursor.getColumnIndexOrThrow("gid"))
            Toast.makeText(context, "First row: uid=$uid, cid=$cid, gid=$gid", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "No data found for cid=$cid", Toast.LENGTH_LONG).show()
        }

        // 关闭 Cursor 和数据库
        cursor.close()
        db.close()
    }

    interface QRCodeScanResultListener {
        fun onQRCodeScanned(result: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
