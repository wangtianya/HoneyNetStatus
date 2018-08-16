/*
 *.
 */
package cn.wangtianya.learn.mvvm;

import java.util.Random;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import cn.wangtianya.learn.R;
import cn.wangtianya.learn.databinding.ActivityDataBindingBinding;

/**
 * setContentView方式，仅适用于activity
 */
public class DataBindingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDataBindingBinding db =  DataBindingUtil.setContentView(this, R.layout.activity_data_binding);


        Article article = new Article();
        article.setTittle("网易的编辑都是XX");

        db.setArticle(article);
    }



    public static class Article {
        private String tittle;


        public String getTittle() {
            return tittle;
        }
        public void setTittle(String tittle) {
            this.tittle = tittle;
        }

        public void onclick(View view) {
            try {
//                ActivityDataBindingBinding db = DataBindingUtil.getBinding((View) view.getParent());
                ActivityDataBindingBinding db = DataBindingUtil.getBinding((View) view.getParent());
                Article article = new Article();
                article.setTittle("Love" + new Random().nextFloat());
                db.setArticle(article);
            } catch (Exception e) {
                Log.e("wangtianya", e.getLocalizedMessage());
            }
        }
    }
}
