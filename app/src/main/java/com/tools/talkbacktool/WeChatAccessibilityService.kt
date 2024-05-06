package com.tools.talkbacktool

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


/**
日期 :2024/4/3
作者:ChenQiXin
描述：
版本：
 */
class WeChatAccessibilityService : AccessibilityService() {

    /**
     * 如：顶部Notification，界面更新，内容变化等，我们可以筛选特定的事件类型，执行不同的响应。
     * 比如：顶部出现WX加好友的Notification Event，跳转到加好友页自动通过
     *
     * @param p0
     */

    var addFriendStep = arrayListOf(0, 0, 0, 0)

    /**
     * 步骤
     */
    private var normalStep = 99

    private var chat=99

    private val TAG = "辅助添加好友"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        Log.d("TalkBack日志", "||eventName：${event?.eventType?.let { getEvenName(it) }}+evenClassName=${event?.className}")

        if (event?.eventType==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            Log.d("TalkBack日志", "evenClassName=${event?.className}")
        }

//
//        findNode2Execute(
//            rootInActiveWindow,
//            "添加到通讯录",
//            null,
//            "android.widget.TextView",
//            ""
//        )







        if(event?.eventType==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED&&event?.className=="com.tencent.mm.plugin.fts.ui.FTSMainUI"){
            normalStep=0
        }
        if (0 == normalStep) {
            try {
                Thread.sleep(600)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            if (EditSearchV()) {
                normalStep = 1
                Log.d("Step", "1填入账号")
                try {
                    Thread.sleep((Math.random() * 2800 + 1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            } else {
                normalStep = 5
            }
        } else if (1 == normalStep) {
            Log.d("Step", "准备搜索")
            if (clickSearch()) {
                normalStep = 2
                Log.d("Step", "搜索完成")
                try {
                    Thread.sleep((Math.random() * 2800 + 1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }else if(2==normalStep){
            Log.d("Step", "资料设置")
            if (infoSet()){
                try {
                    Thread.sleep((1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                clickBack()
                normalStep=3
            }

            try {
                Thread.sleep((Math.random() * 2800 + 1000).toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }else if(3==normalStep){
            Log.d("Step", "准备添加通讯录")
            if (clickAdd()){
                normalStep = 4
                Log.d("Step", "添加通讯录完成")
            }
            try {
                Thread.sleep((Math.random() * 2800 + 1000).toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }else if(4==normalStep){
            Log.d("Step", "准备 发送")
            if (clickSend()){
                normalStep = 5
                Log.d("Step", "发送完成")
            }
            try {
                Thread.sleep((Math.random() * 2800 + 1000).toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
         }






       // addFriend(event)


        //  if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED||event?.eventType==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
        val sourceNode = event?.source

        // 在这里可以处理 sourceNode，例如获取节点文本、ID等信息
//            val nodeList=sourceNode?.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/nvt")
//            if(nodeList!=null&&nodeList.size==4&&addFriendStep[0]==0){
//                Log.d("TalkBack日志", "点击底部微信")
//                nodeList?.get(0)?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                 //addFriendStep[0]=1
//                Log.d("TalkBack日志", "值:${addFriendStep[0]}")
//                return
//            }


        //点击我的
        //延时2s 执行
//            android.os.Handler(Looper.myLooper()!!).postDelayed(
//                { val mineClickNode=nodeList?.get(3)?.performAction(AccessibilityNodeInfo.ACTION_CLICK)},
//                2000
//            )


//            val cancel =sourceNode?.findAccessibilityNodeInfosByText("取消")
//            if(cancel!=null&&cancel.size>0){
//                Log.d("TalkBack日志", "取消")
//                cancel[0]?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//            }


        // }

    };

    /**
     * 搜索账号 聊天记录 等
     *
     * @return
     */
    private fun EditSearchV(): Boolean {
        Log.i(
            TAG,
            "target node text: 搜索, contentDescription: , className: android.widget.EditText"
        )
        return findNode2Execute(
            rootInActiveWindow,
            "搜索",
            null,
            "android.widget.EditText",
            "17366621184"
        )
    }

    /**
     * 搜索
     *、
     * * @return
     */
    private fun clickSearch():Boolean {
        Log.i(
            TAG,
            "target node text: 查找手机, contentDescription: , className: android.widget.EditText"
        )

        return findNode2Execute(
            rootInActiveWindow,
            "查找手机",
            null,
            "android.widget.TextView",
            null
        )

    }



    private fun infoSet():Boolean{
        Log.i(
            TAG,
            "target node text: 通讯录, contentDescription: 更多信息 , className: android.widget.TextView"
        )

        return findNode2Execute(
            rootInActiveWindow,
            null,
            "更多信息",
            "android.widget.ImageView",
            null
        )
    }


    /**
     *  添加好友
     *
     * @return
     */
    private fun clickAdd():Boolean{
        Log.i(
            TAG,
            "target node text: 添加到通讯录, contentDescription: , className: android.widget.TextView"
        )
//
        return findNode2Execute(
            rootInActiveWindow,
            "添加到通讯录",
            null,
            "android.widget.TextView",
            null
        )
    }

    private fun setNike():Boolean{
        Log.i(
            TAG,
            "target node text: 设置备注和标签, contentDescription: , className: android.widget.TextView"
        )
        return findNode2Execute(
            rootInActiveWindow,
            "设置备注和标签",
            null,
            "android.widget.TextView",
            null
        )
    }


    /**
     * 发送
     *
     * @return
     */
    private fun clickSend(): Boolean {
        Log.i(
            TAG,
            "target node text: 发送, contentDescription: , className: android.widget.Button"
        )
        return findNode2Execute(rootInActiveWindow, "发送", null, "android.widget.Button", null)
    }


    private fun clickBack() {
        performGlobalAction(GLOBAL_ACTION_BACK)
    }
    private fun findNode2Execute(
        node: AccessibilityNodeInfo?,
        targetText: String?,
        targetContentDescription: String?,
        targetClassName: String,
        textLabel: String?
    ): Boolean {
        if (null != node) {
            val childnum = node.childCount
            if (childnum == 0) {
                Log.i(
                    TAG,
                    "[findNode2Execute] cnode text: "+node.text +", contentDescription: " + node.contentDescription + ", className: " + node.className+",isClick:"+node.isClickable
                )
                var isTarget = false
                var text: CharSequence? = null
                var contentDescription: CharSequence? = null
                val className = node.className
                if (null != className && className == targetClassName) {
                    if (null != targetText) {
                        text = node.getText()
                        isTarget = if (null != text) {
                            if (targetText != "搜索:" && text == targetText) {
                                if (text == "设置备注和标签") {
                                    return true
                                }
                                true
                            } else if (text.toString().indexOf(targetText) >= 0) {
                                true
                            } else {
                                return false
                            }
                        } else if (targetText == "清除" && node.packageName == "com.tencent.mm") {
                            true
                        } else {
                            return false
                        }
                    } else if (null != targetContentDescription) {
                        contentDescription = node.contentDescription
                        if (null != contentDescription) {
                            isTarget = if (contentDescription == targetContentDescription) {
                                true
                            } else {
                                return false
                            }
                        }
                    } else {
                        return false
                    }
                    if (isTarget) {
                        try {
                            Thread.sleep((Math.random() / 3 * 10000).toLong())
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

//                        Log.i(TAG, node.toString());
//                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        Log.i(TAG, "isClicked is " + node.isClickable());
//                        try{
//                            AccessibilityNodeInfo tn = node.getParent();
//                            int nn = tn.getChildCount();
//                            Log.i(TAG, "nn is " + nn);
//                            for (int n = 0; n < nn; n++) {
//                                Log.i(TAG, "No." + n + " is : " + tn.getChild(n).toString());
//                                tn.getChild(n).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                            }
//
//                            tn.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                            tn.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                            tn.getParent().getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                            tn.getParent().getParent().getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                            tn.getParent().getParent().getParent().getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        }catch (Exception e){
//                            Log.i(TAG, e.toString());
//                        }
                        if (targetClassName == "android.widget.EditText") {
                            return findNode2Edit(node, textLabel?:"账号错误")
                        }
                        return if (node.isClickable) {
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            true
                        } else {
                            try {
                                var tnode = node.getParent()
                                while (!tnode.isClickable) {
                                    tnode = tnode.getParent()
                                }
                                Log.i(
                                    TAG,
                                    "[findNode2Execute] 找到了 tnode text: " + tnode.getText() + ", contentDescription: " + tnode.contentDescription + ", className: " + tnode.className+",isClick:"+tnode.isClickable
                                )
                                tnode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            } catch (e: Exception) {
                                Log.i(TAG, e.toString())
                                return false
                            }
                            true
                        }
                    }
                } else {
                    Log.i(
                        TAG,
                        "current node className is not target, targetClassName: $targetClassName"
                    )
                    return false
                }
            } else {
                var childFlag = false
                for (i in 0 until childnum) {
                    if (childFlag) {
                        findNode2Execute(
                            node.getChild(i),
                            targetText,
                            targetContentDescription,
                            targetClassName,
                            textLabel
                        )
                    } else {
                        childFlag = findNode2Execute(
                            node.getChild(i),
                            targetText,
                            targetContentDescription,
                            targetClassName,
                            textLabel
                        )
                    }
                }
                return childFlag
            }
        }
        return false
    }


    private fun findNode2Edit(node: AccessibilityNodeInfo, textLabel: String): Boolean {
        Log.i(TAG, "findNode2Edit enter.")
        val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", textLabel)
        clipBoard.setPrimaryClip(clip)
        if (node.isEditable) {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            node.performAction(AccessibilityNodeInfo.ACTION_PASTE)
        } else {
            try {
                var tnode = node.getParent()
                while (!tnode.isEditable) {
                    tnode = tnode.getParent()
                }
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                node.performAction(AccessibilityNodeInfo.ACTION_PASTE)
            } catch (e: java.lang.Exception) {
                Log.i(TAG, e.toString())
                return false
            }
        }
        return true
    }





    /**
     * 服务中断回调
     *
     */
    override fun onInterrupt() {
        Log.d("TalkBack日志", " onInterrupt()")
    }


    override fun onServiceConnected() {
        Log.d("TalkBack日志", " onServiceConnected()")
        val serviceInfo = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.DEFAULT
            packageNames = arrayOf("com.tencent.mm") //监听的应用包名，支持多个
            notificationTimeout = 10
        }
        setServiceInfo(serviceInfo)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("TalkBack日志", " onUnbind()")
        return super.onUnbind(intent)

    }


    /**
     *
     *
     */
    fun getEvenName( eventType: Int): String {
        var eventTypeName = ""
        when (eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED -> eventTypeName = "TYPE_VIEW_CLICKED"
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> eventTypeName = "TYPE_VIEW_FOCUSED"
            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> eventTypeName = "TYPE_VIEW_LONG_CLICKED"
            AccessibilityEvent.TYPE_VIEW_SELECTED -> eventTypeName = "TYPE_VIEW_SELECTED"
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> eventTypeName = "TYPE_VIEW_TEXT_CHANGED"
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> eventTypeName =
                "TYPE_WINDOW_STATE_CHANGED"

            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> eventTypeName =
                "TYPE_NOTIFICATION_STATE_CHANGED"

            AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END -> eventTypeName =
                "TYPE_TOUCH_EXPLORATION_GESTURE_END"

            AccessibilityEvent.TYPE_ANNOUNCEMENT -> eventTypeName = "TYPE_ANNOUNCEMENT"
            AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START -> eventTypeName =
                "TYPE_TOUCH_EXPLORATION_GESTURE_START"

            AccessibilityEvent.TYPE_VIEW_HOVER_ENTER -> eventTypeName = "TYPE_VIEW_HOVER_ENTER"
            AccessibilityEvent.TYPE_VIEW_HOVER_EXIT -> eventTypeName = "TYPE_VIEW_HOVER_EXIT"
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> eventTypeName = "TYPE_VIEW_SCROLLED"
            AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED -> eventTypeName =
                "TYPE_VIEW_TEXT_SELECTION_CHANGED"

            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> eventTypeName =
                "TYPE_WINDOW_CONTENT_CHANGED"
        }

        return eventTypeName
    }


}