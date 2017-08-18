# XBaseAdapter

[![](https://www.jitpack.io/v/Yuanarcheannovice/BaseAdapterDemo.svg)](https://www.jitpack.io/#Yuanarcheannovice/BaseAdapterDemo)

RecyclerView 的 通用适配器;  改 鸿婶 的 BaseAdapter

build 依赖：

先在工程build里面先添加这个
```java
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
 ```
 然后在需要使用Module的build中添加：
 ```java
 
 	dependencies {
	        compile 'com.github.Yuanarcheannovice:BaseAdapter:1.2'
	}

 ```

鸿婶 git ( https://github.com/hongyangAndroid/baseAdapter)
# 感谢鸿婶 的开源

先看gif：

![image](https://github.com/Yuanarcheannovice/BaseAdapterDemo/blob/master/gif/adapter.gif)

修改：
没有采用 鸿婶 的装饰着模式, 

因为 需要使用 notifyItemRangeInserted... 
就把 HeaderAndFooterWrapper和MultiItemTypeAdapter和并了，
增加了 RvDataAdapter 对 mDatas 的操作,

```java

 /**
     * 数据集合增加数据 带刷新
     *
     * @param data      数据
     * @param isRefresh 是否刷新
     */
    public void addData(List<T> data, boolean isRefresh) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
            this.mDatas.addAll(data);
            if (isRefresh)
                notifyDataSetChanged();
        } else {
            this.mDatas.addAll(data);
            //因为Header也是占有刷新的 下标，所以需要加上
            notifyItemRangeInserted(mDatas.size() + mHeaderViews.size() - data.size(), data.size());
        }
    }
    ...
```

增加了一个 纯净的adapter，里面只封装了 点击事件  


