
package cn.wangtianya.learn.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.wangtianya.learn.R;

public class HtmlParseActivity extends Fragment {
    String tagName = "selfdefine";
    String tag = "<selfdefine style=\"background-color:#cc0000;color:#FFFFFF\">&nbsp;1号线&nbsp;</selfdefine>&nbsp;"
            + "<selfdefine style=\"background-color:#158e9b;color:#FFFFFF\">&nbsp;4号线大兴线&nbsp;</selfdefine>&nbsp;";

    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_html_parse, null);

        textView = (TextView) view.findViewById(R.id.tv1);

        SpannableStringBuilder love = new SpannableStringBuilder("");
        Pattern pattern = Pattern.compile("(<" + tagName + ".+?</" + tagName + ">)");
        Matcher matcher = pattern.matcher(this.tag);

        while (matcher.find()) {
            String tag = matcher.group();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                love.append(Html.fromHtml(tag, Html.FROM_HTML_MODE_LEGACY, null, new PoiTagHandler()));
            } else {
                love.append(Html.fromHtml(tag, null, new PoiTagHandler()));
            }
        }

        textView.setText(love);

//        textView.setText(Html.fromHtml("<font color=red><b>b</b>b</font>"));

        return view;
    }
}
