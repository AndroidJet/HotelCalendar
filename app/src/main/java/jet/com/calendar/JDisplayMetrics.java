/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jet.com.calendar;

// TODO: Auto-generated Javadoc
/**
 * © 2012 amsoft.cn 名称：AbDisplayMetrics.java 描述：屏幕参数实体
 * 
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-11-13 上午9:00:52
 */
public class JDisplayMetrics {

	/** 屏幕的宽度. */
	public int displayWidth;

	/** 屏幕的高度. */
	public int displayHeight;

	/** 屏幕的像素宽度. */
	public int widthPixels;

	/** 屏幕的像素高度. */
	public int heightPixels;

	/** 屏幕的密度. */
	public float density;

	/** 字体缩放的比例. */
	public float scaledDensity;

	@Override
	public String toString() {
		return "AbDisplayMetrics [displayWidth=" + displayWidth + ", displayHeight=" + displayHeight + ", widthPixels="
				+ widthPixels + ", heightPixels=" + heightPixels + ", density=" + density + ", scaledDensity="
				+ scaledDensity + "]";
	}

}
