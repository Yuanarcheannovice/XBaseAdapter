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
	        compile 'com.github.Yuanarcheannovice:XBaseAdapter:1.2'
	}

 ```
 ```java

简单的介绍一下各个Adapter的作用

目录结构..		

XListBaseAdapter.java	

ListView的适配器简单封装(把设置数据稍微封装了一下,不用每次都写那几个关于数据的方法)

XListViewHolder.java 

网上收集到的一个处理ListView的Adapter的复用工具类

XRvDataAdapter.java	

XRvCommonAdapter.java	

XRvMultiItemTypeAdapter.java	

这三个是改了[张鸿洋]的BaseAdapter,把鸿婶的装饰者模式去掉，然后把 addHeader,addFooter,糅合到MultiItemTypeAdapter里面,

同时继承XRvDataAdapter.java对于数据操作;

具体用法，和鸿婶的BaseAdapter一样的用法

鸿婶 git ( https://github.com/hongyangAndroid/baseAdapter)

感谢鸿婶 的开源


XRvPureAdapter.java	

Recyclerview的Adapter，处理了 点击，长按事件；

处理onCreateViewHolder的方法；

使用时只需要 设置Layout，和绑定数据

XRvPureDataAdapter.java

对XRvPureAdapter.java增加了对数据的操作，
局限于List<T> 集合,如果需要使用Map<Obj,Obj>，直接继承XRvPureAdapter.java即可；

封装了List<T> addDatas 时的动画效果，尽量减少适配器的重复工作；
 ```

