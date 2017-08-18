package cn.viewpager.ziri.pulldownmenu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private  List<String> number;
    private ListView mlistview;
    private PopupWindow mpopwindow;
    private EditText mEditText;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText= (EditText) this.findViewById(R.id.pulldownmenu);
        mImageView= (ImageView) this.findViewById(R.id.arrow);
        number=new ArrayList<String>();
        for(int i=0;i<20;i++)
        {
            number.add(String.valueOf(Math.acos(i)));
        }
        initview();
    }

    private void initview() {
        mlistview= new ListView(this);
        mlistview.setAdapter(new Myadapter(getApplication(),number));
        mlistview.setBackgroundResource(R.drawable.listview_background);
        mlistview.setVerticalScrollBarEnabled(false);/*竖直滚动条*/
        mlistview.setDivider(null);/*分割线*/
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpopwindow=new PopupWindow(getApplicationContext());
                mpopwindow.setWidth(mEditText.getWidth());
                mpopwindow.setHeight(600);
                mpopwindow.setContentView(mlistview);
                mpopwindow.setOutsideTouchable(true);/*如果点击外侧，则将popwindow收起来*/
                mpopwindow.showAsDropDown(mEditText,0,0);

            }
        });
    }

    private  class Myadapter extends BaseAdapter {
        private  Context adpatercontext;
        private List<String> mList;
        public Myadapter(Context mcontext,List<String> mList) {
            this.adpatercontext=mcontext;
            this.mList=mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view==null){
                holder=new ViewHolder();
                view=viewGroup.inflate(getApplicationContext(),R.layout.item_view,null);
                holder.mImageview=(ImageView) view.findViewById(R.id.delete);
                holder.mTextview=(TextView) view.findViewById(R.id.number);
                view.setTag(holder);
            }else{
                holder=(ViewHolder)view.getTag();
            }
            holder.mTextview.setText(mList.get(i));
            holder.mImageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mList.remove(i);
                    Myadapter.this.notifyDataSetChanged();/*刷新listview*/
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*设置输入框*/
                    mEditText.setText(mList.get(i));
                    if(mpopwindow!=null) mpopwindow.dismiss();
                }
            });
            return view;
        }

        /**
         * 缓存ListView
         */
        private class ViewHolder{
            TextView mTextview;
            ImageView mImageview;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
