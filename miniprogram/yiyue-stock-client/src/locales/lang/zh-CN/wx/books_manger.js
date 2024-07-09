export default {
// 文字内容自己添加或者修改
    id: '序号',
    book_name: '书名',
    book_img: '图片',
    book_text: '内容',
    use_state: '借阅状态',
    book_mark: '备注',
    create_date: '创建时间',
// 输入验证提示和placeholder提示
    form_books_manger_info: '对象详情',
    form_id: '请输入序号',
    form_book_name: '请输入书名',
    form_book_img: '请输入图片',
    form_book_text: '请输入内容',
    form_use_state: '请输入借阅状态(0,可借，1已借)',
    form_book_mark: '请输入备注',
    form_create_date: '请输入创建时间'
}

// 下面内容剪切复制到 locales/zh_CN/wx/index.js文件中
// import booksManger from './books_manger'

// 如果没有index.js,新建一个js，内容如下
/*
import booksManger from './books_manger'
export default {
  booksManger
}
*/
// 新建的index.js后，还需要在locales/zh_CN/index.js引入
// import wx from './wx'
