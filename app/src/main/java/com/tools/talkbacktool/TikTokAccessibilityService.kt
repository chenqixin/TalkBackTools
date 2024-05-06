package com.tools.talkbacktool

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.cj.common.log.CJLog

/**
日期 :2024/4/17
作者:ChenQiXin
描述：
版本：
 */
class TikTokAccessibilityService : AccessibilityService() {

    /**
     * 如：顶部Notification，界面更新，内容变化等，我们可以筛选特定的事件类型，执行不同的响应。
     * 比如：顶部出现WX加好友的Notification Event，跳转到加好友页自动通过
     *
     * @param p0
     */

    var addFriendStep = arrayListOf(0, 0, 0, 0)

    /**
     * 关注+私信步骤
     */
    private var attentionAndChatStep = 99


    /**
     * 任务类别 1 关注+私信 2 点赞 3评论
     */
    private var taskType=3

    /**
     * 点赞
     */
    private var likeStep = 99

    /**
     * 评论
     */
    private var commentStep = 99

    private val TAG = "抖音"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d(
            "TalkBack日志",
            "||eventName：${event?.eventType?.let { getEvenName(it) }}+evenClassName=${event?.className}"
        )

//        if (event?.eventType== AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
//            Log.d("TalkBack日志", "evenClassName=${event?.className}")
//


        /**
         * 关注+私信
         */
        if (taskType == 1) {
            attentionAndChat(event)
        }

        /**
         * 点赞
         *
         */

        if(taskType == 2){
            clikLike(event)
        }


        /**
         * 评论
         */

        if (taskType == 3) {
            comment(event)
        }




    }

    private fun clikLike(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            attentionAndChatStep = 0
        }

        if (attentionAndChatStep == 0) {
            CJLog.d("准备点赞")
            var id = "com.ss.android.ugc.aweme:id/erg"
            val targetNode =
                rootInActiveWindow.findAccessibilityNodeInfosByViewId(id)
            if (targetNode != null && targetNode.size > 0&&targetNode[0].contentDescription.isNotEmpty()) {
                if (targetNode[0].contentDescription.contains("未点赞")) {
                    if (clickDYLike(id)) {
                        attentionAndChatStep = 1
                        CJLog.d("点赞成功")
                        attentionAndChatStep = 99
                        taskType =-1
                    }
                    try {
                        Thread.sleep(1000.toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }else{
                    attentionAndChatStep = 99
                    taskType = -1
                    CJLog.d("已经点赞")
                }

            }


        }
    }

    private fun comment(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED&&attentionAndChatStep==99) {
            attentionAndChatStep = 0
        }

        if (attentionAndChatStep == 0) {
            CJLog.d("准备点击评论")
            if (clickDYComment()) {
                CJLog.d("完成点击评论")
                attentionAndChatStep = 1
                try {
                    Thread.sleep((Math.random() * 2800 + 1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }else if(attentionAndChatStep ==1){
            CJLog.d("准备黏贴文本开始评论")
            if (editComment()){
                CJLog.d("黏贴文本开始评论")
                attentionAndChatStep = 2
                try {
                    Thread.sleep((Math.random() * 2800 + 1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }else if(attentionAndChatStep ==2){
            CJLog.d("准备填写评论内容")
            if (pasterComment()){
                CJLog.d("填写评论内容")
                attentionAndChatStep = 3
                try {
                    Thread.sleep((Math.random() * 2800 + 1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        } else if(attentionAndChatStep ==3){
            CJLog.d("准备发送评论")
            if (sendComment()){
                CJLog.d("成功发送评论")
                attentionAndChatStep = 4
                try {
                    Thread.sleep((Math.random() * 2800 + 1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        } else if (attentionAndChatStep == 4) {
            CJLog.d("返回 ${attentionAndChatStep}")
            clickBack()
            try {
                Thread.sleep(1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            clickBack()
            try {
                Thread.sleep(1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            clickBack()
            attentionAndChatStep = 99
            taskType = -1
            CJLog.d("返回 成功 ${attentionAndChatStep}")
        }

    }



    /**
     * 评论
     *
     * @return
     */
    private fun editComment(): Boolean {
        Log.i(
            TAG,
            "target node text: 搜索, contentDescription: , className: android.widget.EditText"
        )
        return findNode2Execute(
            rootInActiveWindow,
            "善语结善缘，恶言伤人心",
            null,
            "android.widget.EditText",
            null,
            true
        )
    }

    /**
     * 评论
     *
     * @return
     */
    private fun pasterComment(): Boolean {
        Log.i(
            TAG,
            "target node text: 善语结善缘，恶言伤人心, contentDescription: , className: android.widget.EditText"
        )
        return findNode2Execute(
            rootInActiveWindow,
            "善语结善缘，恶言伤人心",
            null,
            "android.widget.EditText",
            "cs",
        )
    }


    /**
     * 发送评论
     *
     * @return
     */

    private fun sendComment(): Boolean {
        Log.i(
            TAG,
            "target node text: 善语结善缘，恶言伤人心, contentDescription: , className: android.widget.EditText"
        )
        return findNode2Execute(
            rootInActiveWindow,
            "发送",
            null,
            "android.widget.TextView",
            null,
        )
    }

    private fun attentionAndChat(event: AccessibilityEvent?) {
        /**
         * 关注+私信
         */
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event?.className == "com.ss.android.ugc.aweme.profile.ui.UserProfileActivity" && taskType == 1) {
            attentionAndChatStep = 0
        }

        if (attentionAndChatStep == 0) {
            CJLog.d("准备关注")
            if (clickAttention()) {
                attentionAndChatStep = 1
                CJLog.d("关注成功")
                try {
                    Thread.sleep((Math.random() * 2000 + 1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            try {
                Thread.sleep(1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else if (attentionAndChatStep == 1) {
            CJLog.d("准备掉起私信弹窗")
            if (privateChat()) {
                attentionAndChatStep = 2
                CJLog.d("掉起私信弹窗成功")
            }
            try {
                Thread.sleep(1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else if (attentionAndChatStep == 2) {
            CJLog.d("准备click发送私信")
            if (clickSend()) {
                attentionAndChatStep = 3
                CJLog.d("click发私信成功")

            }

            try {
                Thread.sleep(1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else if (attentionAndChatStep == 3) {
            CJLog.d("准备填写文本")
            if (setEditText()) {
                attentionAndChatStep = 4
                CJLog.d("填写文本完成")
            }

            try {
                Thread.sleep(1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else if (attentionAndChatStep == 4) {
            CJLog.d("准备发送文本")
            if (sendText()) {
                attentionAndChatStep = 5
                CJLog.d("发送文本完成")
            }

            try {
                Thread.sleep(1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else if (attentionAndChatStep == 5) {
            CJLog.d("返回 ${attentionAndChatStep}")
            clickBack()
            try {
                Thread.sleep(1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            clickBack()
            try {
                Thread.sleep(1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            clickBack()
            attentionAndChatStep = 99
            taskType = -1
            CJLog.d("返回 成功 ${attentionAndChatStep}")
        }
    }


    /**
     * 点赞
     *
     * @param id
     * @return
     */
    private fun clickDYLike(id:String):Boolean{
        Log.i(
            TAG,
            "target node text: 点赞, contentDescription:null , className: android.widget.TextView"
        )
        return findNodeById(rootInActiveWindow, id)
    }


    /**
     * 点击评论按钮
     */

    private fun clickDYComment():Boolean{
        Log.i(
            TAG,
            "target node text: 评论, contentDescription:null , className: android.widget.FrameLayout"
        )
         return findNode2Execute(rootInActiveWindow, null, "，按钮", "android.widget.ImageView", null)
    }






    /**
     * 搜索账号 聊天记录 等
     *
     * @return
     */
    private fun clickAttention(): Boolean {
        Log.i(
            TAG,
            "target node text: 关注, contentDescription:null , className: android.widget.TextView"
        )
        return findNodeById(rootInActiveWindow, "com.ss.android.ugc.aweme:id/rup")
    }





    /**
     * 掉起私信弹窗
     *
     * @return
     */
    private fun privateChat(): Boolean {
        Log.i(
            TAG,
            "target node imageView, contentDescription: 更多 , className: android.widget.ImageView"
        )

        return findNode2Execute(
            rootInActiveWindow,
            null,
            "更多",
            "android.widget.ImageView",
            null
        )
    }



    /**
     * 发私信
     *
     * @return
     */
    private fun clickSend(): Boolean {
        Log.i(
            TAG,
            "target node text: 发私信, contentDescription: , className: android.widget.TextView"
        )
        return findNode2Execute(rootInActiveWindow, "发私信", null, "android.widget.TextView", null)
    }


    /**
     * 发送消息
     *
     * @return
     */
    private fun sendText(): Boolean {
        Log.i(
            TAG,
            "target node text: 发送消息, contentDescription: , className: android.widget.EditText"
        )
        return findNode2Execute(
            rootInActiveWindow,
            null,
            "发送",
            "android.widget.ImageView",
            null)
    }


    /**
     * 填写内容
     *
     * @return
     */
    private fun setEditText(): Boolean {
        Log.i(
            TAG,
            "target node text: 搜索, contentDescription: , className: android.widget.EditText"
        )
        return findNode2Execute(
            rootInActiveWindow,
            "发送消息",
            null,
            "android.widget.EditText",
            "hello"
        )
    }



    private fun clickBack() {
        performGlobalAction(GLOBAL_ACTION_BACK)
    }


    /**
     * 根据id 查找节点
     *
     * @param node
     * @param id
     * @return
     */
    private fun findNodeById(
        node: AccessibilityNodeInfo?,
        id: String,
    ): Boolean {
        if (null != node) {
            val targetNode =
                node.findAccessibilityNodeInfosByViewId(id)
            if (targetNode != null && targetNode.size > 0) {
                if (targetNode[0].isClickable) {
                    targetNode[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return true
                } else {
                    var tnode = targetNode[0].getParent()
                    while (tnode.isClickable) {
                        tnode = tnode.getParent()
                    }
                    tnode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return true
                }

            }


        }
        return false
    }


    private fun findNode2Execute(
        node: AccessibilityNodeInfo?,
        targetText: String?,
        targetContentDescription: String?,
        targetClassName: String,
        textLabel: String?,
        onlyClickEdit:Boolean=false
    ): Boolean {
        if (null != node) {
            val childnum = node.childCount
            if (childnum == 0) {
                Log.i(
                    TAG,
                    "[findNode2Execute] cnode text: " + node.text + ", contentDescription: " + node.contentDescription + ", className: " + node.className + ",isClick:" + node.isClickable
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
                            }else if(contentDescription.toString().indexOf(targetContentDescription) >= 0){
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
                        if (targetClassName == "android.widget.EditText"&&!onlyClickEdit){
                                return findNode2Edit(node, textLabel ?: "")
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
                                    "[findNode2Execute] 找到了 tnode text: " + tnode.getText() + ", contentDescription: " + tnode.contentDescription + ", className: " + tnode.className + ",isClick:" + tnode.isClickable
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
        Log.d("TalkBack日志", " DY_onServiceConnected()")
        val serviceInfo = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.DEFAULT
            packageNames = arrayOf("com.ss.android.ugc.aweme") //监听的应用包名，支持多个
            notificationTimeout = 10
        }
        setServiceInfo(serviceInfo)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("TalkBack日志", " Dy_onUnbind()")
        return super.onUnbind(intent)

    }


    /**
     *
     *
     */
    fun getEvenName(eventType: Int): String {
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