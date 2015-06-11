# CommonDialog
一种通用的对话框组件 ，可以自定义多种多样的样式， 继承于DialogFragment 。用法和官方的AlertDialog的用法一致。

### 效果图
![icon](https://github.com/wangjia55/CommonDialog/blob/master/screen/screen1.png)</br>
![icon](https://github.com/wangjia55/CommonDialog/blob/master/screen/screen2.png)</br>

### 用法1 (如果不需要改变按钮的背景，将第二个参数设置成-1)：
 <pre>
 <code>
       AlertDialogFragment dialogFragment = new AlertDialogFragment.Builder(this)
                .setTitle("title")
                .setMessage("message")
                .setPositiveButton("OK", -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();

        dialogFragment.show(getSupportFragmentManager(), "");
</code></pre>
### 用法2(如果不需要改变按钮的背景，将第二个参数设置成-1)：
 <pre>
 <code>
        AlertDialogFragment dialogFragment = new AlertDialogFragment.Builder(this)
                .setMessage("message")
                .setPositiveButton("OK", R.drawable.bg_btn_grey, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel", R.drawable.bg_btn_orange, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();

        dialogFragment.show(getSupportFragmentManager(), "");
</code></pre>

### 这个是系统的AlertDialog的用法，和上面的组建的用法完全一样：
<pre>
 <code>
	new AlertDialog.Builder(self) 
 	.setTitle("确认")
 	.setMessage("确定吗？")
 	.setPositiveButton("是", null)
 	.setNegativeButton("否", null)
 	.show();
</code></pre>

#Author
 Wangjia55(wangjia5509@gmail.com）
### Blog:
  http://blog.csdn.net/wangjia55
  
  #LICENSE
  Copyright (c) 2015 WangJia55 <wangjia5509@gmail.com>
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.
