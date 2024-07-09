export default {
// 文字内容自己添加或者修改
    id: '订单号',
    user_id: '用户',
    library_id: '图书id',
    library_name: '图书名称',
    order_status: '订单状态',
    book_remark: '备注',
    start_date: '借阅时间',
    end_date: '归还时间',
    create_date: '创建时间',
// 输入验证提示和placeholder提示
    form_book_read_info: '对象详情',
    form_id: '请输入订单号',
    form_user_id: '请输入用户',
    form_library_id: '请输入图书id',
    form_library_name: '请输入图书名称',
    form_order_status: '请输入订单状态',
    form_book_remark: '请输入备注',
    form_start_date: '请输入借阅时间',
    form_end_date: '请输入归还时间',
    form_create_date: '请输入创建时间'
}

// 下面内容剪切复制到 locales/zh_CN/wx/index.js文件中
// import bookRead from './book_read'

// 如果没有index.js,新建一个js，内容如下
/*
import bookRead from './book_read'
export default {
  bookRead
}
*/
// 新建的index.js后，还需要在locales/zh_CN/index.js引入
// import wx from './wx'
