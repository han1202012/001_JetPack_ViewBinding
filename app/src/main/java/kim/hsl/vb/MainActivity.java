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
