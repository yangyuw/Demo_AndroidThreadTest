package com.andrew.demo_androidthreadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Android 异步消息处理
//    1. Message
//    Message 是在线程之间传递的消息， 它可以在内部携带少量的信息， 用于在不同线
//    程之间交换数据。上一小节中我们使用到了 Message 的 what 字段，除此之外还可以使
//    用 arg1 和 arg2 字段来携带一些整型数据，使用 obj 字段携带一个 Object 对象。
//    2. Handler
//    Handler 顾名思义也就是处理者的意思，它主要是用于发送和处理消息的。发送消
//    息一般是使用 Handler 的 sendMessage()方法，而发出的消息经过一系列地辗转处理后，
//    最终会传递到 Handler 的 handleMessage()方法中。
//    3. MessageQueue
//    MessageQueue 是消息队列的意思，它主要用于存放所有通过 Handler 发送的消息。
//    这部分消息会一直存在于消息队列中， 等待被处理。 每个线程中只会有一个 MessageQueue
//    对象。
//    4. Looper
//    Looper 是每个线程中的 MessageQueue 的管家，调用 Looper 的 loop()方法后，就会
//    进入到一个无限循环当中，然后每当发现 MessageQueue 中存在一条消息，就会将它取
//    出，并传递到 Handler 的 handleMessage()方法中。每个线程中也只会有一个 Looper 对象。

//    了解了 Message、 Handler、 MessageQueue 以及 Looper 的基本概念后，我们再来对异步
//    消息处理的整个流程梳理一遍。首先需要在主线程当中创建一个 Handler 对象，并重写
//    handleMessage()方法。然后当子线程中需要进行 UI 操作时，就创建一个 Message 对象，并
//    通过 Handler 将这条消息发送出去。之后这条消息会被添加到 MessageQueue 的队列中等待
//    被处理，而 Looper 则会一直尝试从 MessageQueue 中取出待处理消息，最后分发回 Handler
//    的 handleMessage()方法中。 由于 Handler 是在主线程中创建的， 所以此时 handleMessage()方
//    法中的代码也会在主线程中运行，于是我们在这里就可以安心地进行 UI 操作了。
    public static final int UPDATE_TEXT = 1; // indicate update TextView
    private TextView text;
    private Button changeText;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    // handle UI here
                    text.setText("nice to meet u");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        changeText = (Button) findViewById(R.id.change_text);
        changeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);// send Message object
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
