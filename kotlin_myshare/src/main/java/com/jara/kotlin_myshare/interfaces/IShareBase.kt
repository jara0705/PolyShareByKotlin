package com.jara.kotlin_myshare.interfaces

import com.jara.kotlin_myshare.bean.ShareEntity
import com.jara.kotlin_myshare.interfaces.OnShareListener


/**
 * Created by jara on 2017-9-4.
 */

open interface IShareBase {
    fun share(entity: ShareEntity, listener: OnShareListener)
}
