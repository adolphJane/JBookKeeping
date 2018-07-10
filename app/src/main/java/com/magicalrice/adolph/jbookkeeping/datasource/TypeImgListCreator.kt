package com.magicalrice.adolph.jbookkeeping.datasource

import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.ui.addtype.TypeImgBean
import java.util.ArrayList

/**
 * Created by Adolph on 2018/7/10.
 */
object TypeImgListCreator {
    fun createTypeImgBeanData(type: Int): List<TypeImgBean> {
        val list = ArrayList<TypeImgBean>()
        var bean: TypeImgBean

        if (type == RecordType.TYPE_OUTLAY) {
            bean = TypeImgBean("type_eat")
            list.add(bean)

            bean = TypeImgBean("type_calendar")
            list.add(bean)

            bean = TypeImgBean("type_3c")
            list.add(bean)

            bean = TypeImgBean("type_clothes")
            list.add(bean)

            bean = TypeImgBean("type_candy")
            list.add(bean)

            bean = TypeImgBean("type_cigarette")
            list.add(bean)

            bean = TypeImgBean("type_humanity")
            list.add(bean)

            bean = TypeImgBean("type_pill")
            list.add(bean)

            bean = TypeImgBean("type_fitness")
            list.add(bean)

            bean = TypeImgBean("type_sim")
            list.add(bean)

            bean = TypeImgBean("type_study")
            list.add(bean)

            bean = TypeImgBean("type_pet")
            list.add(bean)

            bean = TypeImgBean("type_train")
            list.add(bean)

            bean = TypeImgBean("type_plain")
            list.add(bean)

            bean = TypeImgBean("type_bus")
            list.add(bean)

            bean = TypeImgBean("type_home")
            list.add(bean)

            bean = TypeImgBean("type_wifi")
            list.add(bean)

            bean = TypeImgBean("type_insure")
            list.add(bean)

            bean = TypeImgBean("type_outlay_red")
            list.add(bean)
        } else {
            bean = TypeImgBean("type_salary")
            list.add(bean)

            bean = TypeImgBean("type_pluralism")
            list.add(bean)

            bean = TypeImgBean("type_wallet")
            list.add(bean)

            bean = TypeImgBean("type_income_red")
            list.add(bean)
        }
        return list
    }
}