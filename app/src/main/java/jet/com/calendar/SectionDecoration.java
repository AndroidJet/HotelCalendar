package jet.com.calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * @author byd666
 * @date 2017/6/15
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SectionDecoration";

    private PowerGroupListener mGroupListener;
    /**
     *     悬浮栏高度
     */
    private int mGroupHeight = 80;
    /**
     *     是否靠左边
     */
    private boolean isAlignLeft = true;

    private Context mContext;

    private SectionDecoration(Context contex, PowerGroupListener groupListener) {
        this.mGroupListener = groupListener;
        mContext=contex;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        String groupId = getGroupName(pos);
        if (groupId == null) {return;}
        //只有是同一组的第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = mGroupHeight;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //获取单条的数目
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        //得出距离左边和右边的padding
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        //开始绘制
        String preGroupName;
        String currentGroupName = null;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupName = currentGroupName;
            currentGroupName = getGroupName(position);
            if (currentGroupName == null || TextUtils.equals(currentGroupName, preGroupName))
            {
                continue;
            }
            int viewBottom = view.getBottom();
            //top 决定当前顶部第一个悬浮Group的位置
            int top = Math.max(mGroupHeight, view.getTop());
            if (position + 1 < itemCount) {
                //获取下个GroupName
                String nextGroupName = getGroupName(position + 1);
                //下一组的第一个View接近头部
                if (!currentGroupName.equals(nextGroupName) && viewBottom < top) {
                    top = viewBottom;
                }
            }

            //根据position获取View
            View groupView = getGroupView(position);
            if (groupView == null){ return;}
            int sWidth=AppUtil.getDisplayMetrics(mContext).displayWidth;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(sWidth, mGroupHeight);
//            layoutParams.gravity=Gravity.CENTER;
            groupView.setLayoutParams(layoutParams);
            groupView.setDrawingCacheEnabled(true);
            groupView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            //指定高度、宽度的groupView
            groupView.layout(0, 0, right, mGroupHeight);
//            groupView.getLayoutParams().
            groupView.buildDrawingCache();

            Bitmap bitmap = groupView.getDrawingCache();
            int marginLeft =0;
            LogUtil.debugE("right",right+"");
            LogUtil.debugE("groupView.getMeasuredWidth()",groupView.getMeasuredWidth()+"");
            c.drawBitmap(bitmap,0, top - mGroupHeight, null);
        }
    }

    /**
     * 判断是不是组中的第一个位置
     * 根据前一个组名，判断当前是否为新的组
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            String prevGroupId = getGroupName(pos - 1);
            String groupId = getGroupName(pos);
            return !TextUtils.equals(prevGroupId, groupId);
        }
    }

    /**
     * 获取组名
     * @param position
     * @return 组名
     */
    private String getGroupName(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupName(position);
        } else {
            return null;
        }
    }

    /**
     * 获取组View
     * @param position
     * @return 组名
     */
    private View getGroupView(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupView(position);
        } else {
            return null;
        }
    }

    public static class Builder {
        SectionDecoration mDecoration;
        private Builder(Context context, PowerGroupListener listener) {
            mDecoration = new SectionDecoration(context,listener);
        }
        /**
         * 初始化 listener
         * @param listener
         * @return
         */
        public static Builder init(Context context, PowerGroupListener listener) {
            return new Builder(context,listener);
        }
        /**
         * 设置Group高度
         * @param groutHeight 高度
         * @return this
         */
        public Builder setGroupHeight(int groutHeight) {
            mDecoration.mGroupHeight = groutHeight;
            return this;
        }
        /**
         * 是否靠左边
         * true 靠左边（默认）、false 靠右边
         * @param b b
         * @return  this
         */
        public Builder isAlignLeft(boolean b){
            mDecoration.isAlignLeft = b;
            return this;
        }
        public SectionDecoration build() {
            return mDecoration;
        }
    }

}
