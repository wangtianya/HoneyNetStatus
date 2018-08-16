/*
 *.
 */
package cn.wangtianya.learn.mvvm;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import cn.wangtianya.learn.R;
import cn.wangtianya.learn.databinding.ActivityMvvmDbMubanBinding;

/**
 * setContentView方式，仅适用于activity
 */
public class DataBindingMubanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         ActivityMvvmDbMubanBinding db =
                DataBindingUtil.setContentView(this, R.layout.activity_mvvm_db_muban);

        Article article = new Article();
        article.tittle = "网易的编辑都是XX";

        db.setArticle(article);
    }

    public static class Article extends BaseObservable {
        public String tittle;

        public Author author;

        public String getTittle() {
            return "asdf1234";
        }
    }


    public static class Author extends BaseObservable {
        private String name = "王大哥";

        public String getName() {
            return name;
        }
    }
}
