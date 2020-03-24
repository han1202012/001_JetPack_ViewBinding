# 【JetPack 架构组件】ViewBinding 视图绑定组件 ( 启用模块 | 视图绑定定制 | 绑定类名称生成规则 | 绑定类字段生成规则 | 绑定类获取根视图 | 绑定类获取布局组件 )


https://hanshuliang.blog.csdn.net/article/details/105044345



<br>
<br>

#### I . 视图绑定组件简介

---

<br>


**Activity 获取布局组件方式 :** <font color=blue>在之前的 Activity 代码中 , 使用 findViewById ( ) 方法 , 或者 Butter Knife 的 @BindView 注解方式获取 Layout 布局中的组件对象 ; 

<br>

<font color=red>**视图绑定 ( ViewBinding ) 是 Google 退出的新的获取布局组件的方式 , 顾名思义 , 其作用就是将 Layout 布局中的 View 组件绑定在 Activity 等界面的 Java / Kotlin 代码中 , 可以在代码中获取这些组件对象 ;**  




<br>
<br>

#### II . 视图绑定 ViewBinding 使用前提 ( Android Studio 3.6 )

---

<br>



**1 . Android Studio 最低版本要求 :** <font color=purple>使用视图绑定组件 , 需要将 Android Studio 开发环境升级到 3.6 Canary 11 及以上版本 ; 

<br>

**2 . 当前最新稳定版本 :** <font color=blue>目前 2020/03/23 最新稳定版本是 3.6.1 , 建议升级到该版本 , 不推荐使用 4.0 或 4.1 BETA 版本 ; 

<br>

><font color=brown>估计大部分开发者都需要升级 Android Studio 版本到 3.6 以上 , 这里给简单的出升级流程 , 以作参考 ; ( 大概需要 10 分钟左右 )

<br>

**3 . Android Studio 升级流程 :** <font color=red>尽量使用 Android Studio 自带的升级功能 , 这样能尽量少的降低对之前应用的影响 ; 

<br>

**① 选择 Android Studio 自带升级选项 :** <font color=green>选择菜单栏 ,  Help -> Check for Updates ... , 弹出下面的对话框 , 选择 Update and Restart 选项 ; 
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200323112844240.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)

<br>


**② 等待下载更新 :** <font color=orange>等待下载 , 下载完毕后会自动安装应用 ; 

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200323113145451.png)

<br>


**③ 删除旧版本的相关目录 :** <font color=cyan.>为了避免影响新版本 Android Studio 开发环境运行 , 删除旧版本的相关文件 ; 
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200323113723159.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)

<br>

**④ 新版本自动启动 :** 

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020032311353943.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)


<br>
<br>

#### III . 视图绑定组件启用 

---

<br>

**启用视图绑定模块 :** <font color=red>**在 Module 中的 build.gradle 构建脚本中的 android 下 , 配置如下内容 :** 

```java
viewBinding {
    enabled = true
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200323230501883.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)

<br>
<br>

#### IV . 定制视图绑定 ( 启用视图绑定后 不想生成绑定类 )

---

<br>

**全部布局默认进行视图绑定 :** <font color=green>只要在 build.gradle 中启用了 视图绑定 , 那么系统会默认为每个 XML 布局文件生成一个绑定类 ;

<br>

**定制不生成绑定类的情况 :** <font color=blue>在布局根标签中配置 <font color=red>**tools:viewBindingIgnore="true"** <font color=blue>属性 , 即不为该布局生成绑定类 ; 
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200323230757856.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)


<br>
<br>

#### V . 视图绑定布局文件

---

<br>

**下面的绑定类定义了三个 TextView , 前两个定义了 id 分别是 <font color=red>text_view <font color=black>和 <font color=red>text_view2 <font color=black>, 最后一个没有定义 id ;** 

**该布局作为视图绑定的示例布局 , 下面的内容都以该布局为基础讲解 ;**


<br>

<font color=red>**activity_main.xml** 

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <TextView
        android:id="@+id/text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World 1!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.3" />

    <TextView
        android:id="@+id/text_view2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World 2!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.5"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World 3!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.7"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```


<br>
<br>

#### VI . 绑定类名称生成规则

---

<br>


**视图绑定类名称生成规则 :** <font color=red>去掉布局文件名称的下划线 , 并以驼峰式命名 , 在名称结尾添加 Binding 后缀 ;

<br>

<font color=blue>以本 Activity 的布局文件 activity_main.xml 为例 , 删除中间的下划线 , Activity 和 Main 首字母大写 , 在后面添加 Binding 后缀 , 生成的绑定类名称为 ActivityMainBinding ; 


<br>
<br>

#### VII . 绑定类对应的布局中的组件字段生成规则

---

<br>

**1 . 生成的绑定类字段 :** <font color=magenta>只要在布局文件中定义了 id 属性的组件 , 绑定类中就会为该组件生成相应的字段 ; 

<br>

**① 绑定类字段对应布局 ID :**  <font color=green>activity_main.xml 布局文件中 , 第一个和第二个 TextView 都定义了 id 分别是 text_view 和 text_view2 ; 

**② 绑定类生成的字段 :** <font color=purple>那么系统会在 ActivityMainBinding 中生成 TextView text_view 和 TextView text_view2 两个字段 ; 

**③ 绑定类组件字段访问 :** <font color=orange>通过 ActivityMainBinding 对象可以直接访问这两个组件 ; 

<br>

**2 . 不生成字段 :** <font color=brown>第三个 TextView 没有定义 id 属性 , ActivityMainBinding 中不会生成该组件对应的字段 ; 




<br>
<br>

#### VIII . 视图绑定类获取

---

<br>

**activity_main.xml 布局生成的绑定类为 ActivityMainBinding , 可调用 inflate 方法 , 获取该绑定类对象 , 需要传递 LayoutInflater 参数 , 可以直接调用 Activity 的 getLayoutInflater() 方法获取 ;** 

<br>

```java
/**
 * 视图绑定类 对象
 *      binding 中可以获取布局文件中定义的
 *      text_view 和 text_view2 两个 TextView 组
 *
 */
private ActivityMainBinding binding;


/*
    获取 视图绑定 对象
    生成绑定类 : 需要传递 LayoutInflater 参数 ,
        可以直接调用 Activity 的 getLayoutInflater() 方法获取
 */
binding = ActivityMainBinding.inflate(getLayoutInflater());

```


<br>
<br>

#### IX . 设置视图绑定后的布局

---

<br>

**1 . 获取根视图 :**  <font color=red>ActivityMainBinding 绑定类自带 getRoot() 方法 , 可以直接获取到 布局文件的 根视图 ;

**2 . 设置 Activity 视图 :**  <font color=blue>这里可以直接将根视图传递给 setContentView 函数作为参数 , 即可在该 Activity 中显示该布局 ;

<br>

```java
/*
    ActivityMainBinding 绑定类自带 getRoot() 方法
        可以直接获取到 布局文件的 根视图
    这里可以直接将根视图传递给 setContentView 函数作为参数 , 即可在该 Activity 中显示该布局
 */
setContentView(binding.getRoot());
```


<br>
<br>

#### X . 获取视图绑定类组件

---

<br>

**可以直接通过 视图绑定类 ActivityMainBinding 对象 binding 获取对应的组件 , 组件名称就是布局文件中定义的组件 id ;** 

```java
//binding 中可以直接通过组件 id 获取对应的组件
binding.textView.setText("ActivityMainBinding 1");
binding.textView2.setText("ActivityMainBinding 2");
```


<br>
<br>

#### XI . 视图绑定对应 Java 示例代码 

---

<br>


```java
package kim.hsl.vb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import kim.hsl.vb.databinding.ActivityMainBinding;

/**
 *
 * 视图绑定 ( ViewBinding ) 只绑定布局文件中定义了 id 属性的组件
 *      那些没有定义 id 属性的组件不绑定
 *
 *  只要在 build.gradle 中启用了 视图绑定 ,
 *      那么系统会默认为每个 XML 布局文件生成一个绑定类 ;
 *      ( 可以在布局中设置 tools:viewBindingIgnore="true" 不生成绑定类 )
 *
 *
 *  视图绑定类生成规则 : 去掉布局文件名称的下划线 , 并以驼峰式命名 , 在名称结尾添加 Binding 后缀 ;
 *      以本 Activity 的布局文件 activity_main.xml 为例 :
 *          删除中间的下划线 , Activity 和 Main 首字母大写 , 在后面添加 Binding 后缀
 *          生成的绑定类名称为 ActivityMainBinding
 *
 *   生成的绑定类字段 : 只要在布局文件中定义了 id 属性的组件 , 绑定类中就会为该组件生成相应的字段
 *      如 :
 *
 *      生成字段 : activity_main.xml 布局文件中 , 第一个和第二个 TextView 都定义了 id 分别是
 *      text_view 和 text_view2 ,
 *      那么系统会在 ActivityMainBinding 中生成 TextView text_view 和 TextView text_view2 两个字段
 *      通过 ActivityMainBinding 对象可以直接访问这两个组件
 *
 *      不生成字段 : 第三个 TextView 没有定义 id 属性 , ActivityMainBinding 中不会生成该组件对应的字段
 *
 *
 *  不生成绑定类的情况 : 在布局根标签中配置 tools:viewBindingIgnore="true" 属性 , 即不为该布局生成绑定类
 *
 *
 *   视图绑定 与 findViewById 对比 : 避免了很多问题出现 ;
 *      空指针优化 : 视图绑定 针对一个布局进行自动生成字段 , 不会出现 ID 无效导致的空指针情况 ;
 *      类型安全优化 : 视图绑定 的类型都是自动生成好的 , 不会出现用户自己定义类型 , 导致组件类型转换错误的情况 ;
 *
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 视图绑定类 对象
     *      binding 中可以获取布局文件中定义的
     *      text_view 和 text_view2 两个 TextView 组件
     *
     */
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            获取 视图绑定 对象

            生成绑定类 : 需要传递 LayoutInflater 参数 ,
                可以直接调用 Activity 的 getLayoutInflater() 方法获取
         */
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        /*
            ActivityMainBinding 绑定类自带 getRoot() 方法
                可以直接获取到 布局文件的 根视图

            这里可以直接将根视图传递给 setContentView 函数作为参数 , 即可在该 Activity 中显示该布局
         */
        setContentView(binding.getRoot());

        //binding 中可以直接通过组件 id 获取对应的组件
        binding.textView.setText("ActivityMainBinding 1");
        binding.textView2.setText("ActivityMainBinding 2");
    }
}

```

<br>
<br>

#### XII . 应用运行结果

---

<br>

**APP 运行结果 :** <font color=red>可以看到通过绑定类获取 TextView , 设置其文字 , 分别为 ActivityMainBinding 1 和 ActivityMainBinding 2 已经设置成功 , 说明视图绑定操作完成 ; 

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200323232936364.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)


<br>
<br>

#### XIII . GitHub 代码地址

---

<br>

**GitHub 代码地址 : [https://github.com/han1202012/001_JetPack_ViewBinding](https://github.com/han1202012/001_JetPack_ViewBinding)**

