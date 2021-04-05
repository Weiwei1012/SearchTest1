package com.example.searchrecyclerviewexample

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var adapter: RecyclerView_Adapter
    lateinit var hotlist_rv: RecyclerView
    lateinit var gridLayoutManager: GridLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //製作熱門搜尋的recycler_view
        hotlist_rv = findViewById(R.id.hotlist_rv)
        gridLayoutManager = GridLayoutManager(
            hotlist_rv.context, 2,
            LinearLayoutManager.VERTICAL, false
        )
        hotlist_rv.layoutManager = gridLayoutManager
        hotlist_rv.setHasFixedSize(true)

        //將所有可搜尋的食材(存在 strings.xml)載入（此list不會顯示在螢幕上，只供我們在後台管理用的）
        val all_ingredients: Array<String> = resources.getStringArray(R.array.ingredients)

        //設定searchView輸入text的listener
        ingredients_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //當送出input, input的字串為query(資料型態為String?)
            override fun onQueryTextSubmit(query: String?): Boolean {

                //將使用者input的食材（們）依據"(空白鍵)"分別存入ingredients_array
                val ingredients_array = query?.split(" ")!!.toTypedArray()

                //設定一個outputList用來存入符合規定（找得到對應料理）的食材
                //將物件放入array中，其中一種方法就是使用MutableList
                //
                val outputList: MutableList<String> = ArrayList()
                for (i in ingredients_array.indices){

                    //判斷是否有在可搜尋的食材中
                    if (all_ingredients.contains(ingredients_array[i])){
//                        var ingredients_true = arrayOf<String>()
//                        ingredients_true += ingredients_array[i]

                        //若有，存入輸出array
                        outputList.add(ingredients_array[i])

                    }
//
                }

                //透過Intent將輸出array傳到DetailsActivity，名稱為"passsearched"
                //若要將array透過intent傳送，需使用putStringArrayListExtra來實作
                val intent = Intent(baseContext, DetailsActivity::class.java)
                // distinct 可過濾在陣列中重複的字串
                intent.putStringArrayListExtra("passsearched", ArrayList(outputList.distinct()))
                startActivity(intent)

//                else{
//                    Toast.makeText(applicationContext,"無此食材料理",Toast.LENGTH_LONG).show()
//                }
                return false
            }

            //當query改變（主要作用於：刪掉變成”(空白)“時）一開始下方的recyclerView（也就是我們預設的熱門收尋）
            //需要重新出現
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        //getList() 為搜尋條下方（熱門搜尋）的function
        getList()
    }

    private fun getList() {
        //設定熱門搜尋的選項們
        val hotlist_items = arrayOf("快速晚餐","高麗菜","馬鈴薯","簡易家常菜","雞肉","超簡單甜點","家常菜 肉","減醣")

        //因為RecyclerView_Adapter所要的參數為ArrayList型態
        val hotList = ArrayList<String>()
        //因此需另外將Array中的string加入到ArrayList中
        for (i in hotlist_items) {
            hotList.add(i)
        }
        //將熱門搜尋RecyclerView的adapter設定為傳入hotList參數的R_A
        adapter = RecyclerView_Adapter(hotList)
        hotlist_rv.adapter = adapter
    }


}
