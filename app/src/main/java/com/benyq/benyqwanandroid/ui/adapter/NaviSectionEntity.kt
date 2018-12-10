package com.benyq.benyqwanandroid.ui.adapter

import com.benyq.benyqwanandroid.api.model.NavigationModel
import com.benyq.benyqwanandroid.base.adapter.SectionEntity

/**
 *@author benyq
 *@e-mail 1520063035@qq.com
 *@Date 2018/12/8
 */
class NaviSectionEntity(isHeader: Boolean, head: String, data: NavigationModel.Article? = null): SectionEntity<NavigationModel.Article>(isHeader, head, data) {
    constructor(data: NavigationModel.Article): this(false, "", data)
}