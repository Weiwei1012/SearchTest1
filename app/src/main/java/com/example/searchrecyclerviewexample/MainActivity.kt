package com.example.searchrecyclerviewexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
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
                //val outputList: MutableList<String> = ArrayList()
                val outputList: MutableList<String> = ArrayList()
                for (i in ingredients_array.indices){

                    //判斷是否有在可搜尋的食材中
                    if (all_ingredients.contains(ingredients_array[i])){
//                        var ingredients_true = arrayOf<String>()
//                        ingredients_true += ingredients_array[i]

                        //若有，存入輸出array
                        //outputList.add(ingredients_array[i])
                        when(ingredients_array[i]){
                            "燕麥" -> getItemFoodList(outputList,resources.getStringArray(R.array.燕麥))
                            "牛奶" -> getItemFoodList(outputList,resources.getStringArray(R.array.牛奶))
                            "雞蛋"-> getItemFoodList(outputList,resources.getStringArray(R.array.蛋))
                            "蛋" -> getItemFoodList(outputList,resources.getStringArray(R.array.蛋))
                            "青蔥"-> getItemFoodList(outputList,resources.getStringArray(R.array.蔥))
                            "蔥" -> getItemFoodList(outputList,resources.getStringArray(R.array.蔥))
                            "洋蔥" -> getItemFoodList(outputList,resources.getStringArray(R.array.蔥))
                            "菇"  -> getItemFoodList(outputList,resources.getStringArray(R.array.菇))
                            "香菇"  -> getItemFoodList(outputList,resources.getStringArray(R.array.菇))
                            "菇類"  -> getItemFoodList(outputList,resources.getStringArray(R.array.菇))
                            "水果" -> getItemFoodList(outputList,resources.getStringArray(R.array.水果))
                            "果醬" -> getItemFoodList(outputList,resources.getStringArray(R.array.果醬))
                            "奶油" -> getItemFoodList(outputList,resources.getStringArray(R.array.奶油))
                            "吐司" -> getItemFoodList(outputList,resources.getStringArray(R.array.吐司))
                            "去骨雞腿" -> getItemFoodList(outputList,resources.getStringArray(R.array.去骨雞腿肉))
                            "去骨雞腿肉" -> getItemFoodList(outputList,resources.getStringArray(R.array.去骨雞腿肉))
                            "優格" -> getItemFoodList(outputList,resources.getStringArray(R.array.優格))
                            "雞胸" -> getItemFoodList(outputList,resources.getStringArray(R.array.雞胸))
                            "雞胸肉" -> getItemFoodList(outputList,resources.getStringArray(R.array.雞胸))
                            "雞腿" -> getItemFoodList(outputList,resources.getStringArray(R.array.雞胸))
                            "雞腿肉" -> getItemFoodList(outputList,resources.getStringArray(R.array.雞腿))
                        }

                    }
//
                }


                //透過Intent將輸出array傳到DetailsActivity，名稱為"passsearched"
                //若要將array透過intent傳送，需使用putStringArrayListExtra來實作
                val intent = Intent(baseContext, DetailsActivity::class.java)

                // distinct 可過濾在陣列中重複的字串
                intent.putStringArrayListExtra("passsearched", ArrayList(outputList.distinct()))
                //intent.putStringArrayListExtra("passsearched", ArrayList(outputList))

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

    private fun getItemFoodList(output: MutableList<String>, ingredients_map: Array<String>){
        output.addAll(ingredients_map)
    }

//    private fun CountingSort(input: MutableList<String>): List<String> {
//
//        val output: MutableList<String>
//
//        val counter: Array<Int>
//        for (i in input.indices){
//
//        }
//
//
//        return output.distinct()
//    }


}
