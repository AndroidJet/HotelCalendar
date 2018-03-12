package jet.com.calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String mServiceDate;

    private int startGroup=-1;
    private int endGroup=-1;
    private int startchild=-1;
    private int endchild=-1;

    //选中的起始时间
    private String mStartTime;
    //结束时间
    private String mEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //该时间应该是服务器时间2018-02-03
        mServiceDate="2018-04-03";

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePopupWindow popupWindow=new DatePopupWindow(MainActivity.this,mServiceDate);
                //根据4个参数初始化
                if(startchild!=-1 && startGroup!=-1 && endGroup!=-1 && endchild!=-1){
                    popupWindow.setInit(startGroup,startchild,endGroup,endchild);
                }else {
                    //设置根据mServiceDate设定今天和明天日期
                    popupWindow.setDefaultSelect();
                }
                popupWindow.setDateOnClickListener(new DatePopupWindow.DateOnClickListener() {
                    @Override
                    public void getDate(List<DateInfo> list, int startGroupPosition, int startChildPosition, int endGroupPosition, int endChildPosition) {
                        startGroup=startGroupPosition;
                        startchild=startChildPosition;
                        endGroup=endGroupPosition;
                        endchild=endChildPosition;
                        mStartTime=CalendarUtil.FormatDate(list.get(startGroupPosition).getList().get(startChildPosition).getDate());

                        mEndTime=CalendarUtil.FormatDate(list.get(endGroupPosition).getList().get(endChildPosition).getDate());

                    }
                });
                popupWindow.create(view);
            }
        });
    }
}
