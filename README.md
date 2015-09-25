# BuilderDialog<br />
采用Builder设计模式对Dilaog进行设计，可制定度较高。【根据自己使用的项目编写】<br />
#功能列表<br />
1、内容为普通文字<br />
2、内容为文字+图片<br />
3、内容为自定义内容<br />
4、动画样式<br />
5、外部点击不可关闭设置<br />
6、用户自己处理dismiss<br />
7、底部分享对话框<br />
8、内容padding和底部margin<br />
#screenshots<br />
![image](https://github.com/chuangWu/BuilderDialog/blob/master/screenshots/dialog.gif)<br />
#使用方法<br />

<pre name="code" class="java">   DialogBuilder.Builder(this)                                       //传context
                .content(R.layout.ly_dialog_textview)               //显示的内容View，支持resId和View
                .beginConfig()                                     //开始配置
                .title(&quot;这是标题&quot;)                                //标题
                .text(&quot;这是内容&quot;)                                //如果内容View中必须包含id为text的TextView才能使用此属性
                .theme(R.style.dialog_style1)                   //dialog 样式
                .ok(new View.OnClickListener() {               // 确定按钮处理、文字可指定，比如ok(&quot;立即更新&quot;,onClickListener)
                    @Override
                    public void onClick(View view) {

                    }
                })
                .cancel(new View.OnClickListener() {        //取消按钮处理、文字可指定
                    @Override
                    public void onClick(View view) {

                    }
                })
                .endConfig()                           //结束配置
                .build();                             //构建并显示dialog</pre>
<br />
<br />
