package com.team4.cardcase2.foreground

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team4.cardcase2.R
import com.team4.cardcase2.entity.ServerCard
import com.team4.cardcase2.entity.WholeServerCard
import com.team4.cardcase2.interfaces.HttpRequest
import com.team4.cardcase2.interfaces.test
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupFragment : Fragment() , ButtonClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var groupAdapter:GroupAdapter
    private lateinit var cardAdapter: CardAdapter
    private var cardLists: MutableList<ServerCard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_group, container, false)
        val recyclerView: RecyclerView =root.findViewById(R.id.groupView)
        val groupLists = getGids(1)
        groupAdapter = GroupAdapter(groupLists, this)
        recyclerView.adapter = groupAdapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val addGroupButton: Button = root.findViewById(R.id.addGroupButton)
        addGroupButton.setOnClickListener {
            showInputDialog()
        }

        val cardView: RecyclerView = root.findViewById(R.id.cardView)
//        getCards(1, "1")
        cardLists = test.getMyCard()
//        Toast.makeText(context, "Response: $cardLists", Toast.LENGTH_SHORT).show()
        cardAdapter = CardAdapter(cardLists)
        cardView.adapter = cardAdapter
        cardView.layoutManager = LinearLayoutManager(requireContext())

        return root
    }

    private fun showInputDialog() {
        // 创建一个EditText用于输入
        val inputEditText = EditText(requireContext())
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Enter Group Name")
            .setView(inputEditText)
            .setPositiveButton("OK") { _, _ ->
                val inputText = inputEditText.text.toString()
                Toast.makeText(requireContext(), inputText, Toast.LENGTH_SHORT).show()
                if (inputText.isNotEmpty()) {
                    // 添加到RecyclerView
                    groupAdapter.addItem(inputText)
                    addGid(1, inputText)
                    // 显示Toast
                    Toast.makeText(requireContext(), inputText, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
    private fun addGid(uid: Int, gid: String){
        val db: SQLiteDatabase = requireContext().openOrCreateDatabase("sqlite.db", Context.MODE_PRIVATE, null)

        // 查询是否已有相同的 uid 和 gid
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM SQLTable WHERE uid = ? AND gid = ?", arrayOf(uid.toString(), gid))

        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()

        if (count == 0) {
            // 如果不存在，则插入新的记录
            val contentValues = ContentValues().apply {
                put("uid", uid)
                put("cid", -1)
                put("gid", gid)
            }
            db.insert("SQLTable", null, contentValues)
        }

        db.close()
    }
    private fun getGids(uid: Int): MutableList<String> {
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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override suspend fun rowClick(id: Int, text: String) {

        cardLists = getCards(id, text)
        if (id==0){
            cardLists = test.getMyCard1()
        }
        if (id==2){
            cardLists = test.getMyCard2()
        }
        if (id==1){
            cardLists = test.getMyCard()
        }
//        Toast.makeText(context, "QR Code Result: ${getCards(id, text)}", Toast.LENGTH_LONG).show()
        cardAdapter = CardAdapter(cardLists)


    }

    private suspend fun getCards(uid: Int, text: String): MutableList<ServerCard>{
        val cardList: MutableList<ServerCard> = mutableListOf()
        val cidList = getCidList(uid, text)

//        Toast.makeText(context, "Response: $cidList", Toast.LENGTH_SHORT).show()
        for (cid in cidList){
            MainScope().launch {
                getCard(cid)
            }
        }
        Toast.makeText(context, "Response: $cardList", Toast.LENGTH_LONG).show()
        cardAdapter = CardAdapter(cardList)
        return cardList
    }

    private suspend fun getCard(
        cid: Int
    ){
        val authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6IjE5NzIzNTY5NThAcXEuY29tIiwiZXhwIjoxNzE4NTIwMjQyfQ.mXJxuhtmxTx3_yvKv1MOgnGEccK7h_f21ugT-bvuViI"

        val url = "https://578q07p228.vicp.fun/api/cards/${cid}"
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
                    val wholeServerCard: WholeServerCard =
                        WholeServerCard.fromJson(response!!)
                    val card = wholeServerCard.card
    //                        Toast.makeText(context, "Response: $wholeServerCard", Toast.LENGTH_LONG).show()
    //                        Toast.makeText(context, "Response: $card", Toast.LENGTH_LONG).show()
                    if (card != null) {
                        cardLists.add(card)
                    }
//                    Toast.makeText(context, "Response: $cardList", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getCidList(uid: Int, gid: String): MutableList<Int>{
        val db: SQLiteDatabase = requireContext().openOrCreateDatabase("sqlite.db", Context.MODE_PRIVATE, null)
        val cidList = mutableListOf<Int>()
        val cursor: Cursor = db.rawQuery("SELECT cid FROM SQLTable WHERE uid = ? AND gid = ?", arrayOf(uid.toString(), gid))

        if (cursor.moveToFirst()) {
            do {
                val cid = cursor.getInt(cursor.getColumnIndexOrThrow("cid"))
                cidList.add(cid)
//                Toast.makeText(context, "Response: $cid", Toast.LENGTH_SHORT).show()
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return cidList
//        return mutableListOf(48)
    }
}