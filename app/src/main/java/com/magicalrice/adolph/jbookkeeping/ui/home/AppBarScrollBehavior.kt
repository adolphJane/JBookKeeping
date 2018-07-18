package com.magicalrice.adolph.jbookkeeping.ui.home

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View

/**
 * 上滑时当AppBarLayout由展开到收起时，会依次调用onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onStopNestedScroll()
 *
 * 当AppBarLayout收起后继续向上滑动时，会依次调用onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onNestedScroll()->onStopNestedScroll()
 *
 * 下滑时当AppBarLayout全部展开时（即未到顶部时），会依次调用onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onNestedScroll()->onStopNestedScroll()
 *
 * 当AppBarLayout全部展开时（即到顶部时），继续向下滑动屏幕，会依次调用onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onNestedScroll()->onStopNestedScroll()
 *
 * 当有快速滑动时会在onStopNestedScroll()前依次调用onNestedPreFling()->onNestedFling()
 *
 * 所以要修改AppBarLayout的越界行为可以重写onNestedPreScroll()或onNestedScroll()，因为AppBarLayout收起时不会调用onNestedScroll()，所以只能选择重写onNestedPreScroll()。
 *
 *
 * Created by Adolph on 2018/7/16.
 */
class AppBarScrollBehavior : AppBarLayout.Behavior() {
    override fun onStartNestedScroll(parent: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type)
    }
}