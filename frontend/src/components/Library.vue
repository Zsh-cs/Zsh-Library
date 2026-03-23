<script setup>
// reative用来存一组相关联的数据，比如书名和作者
// ref用来存简单的东西，比如对话框显示或隐藏
// ElMessage用于在全局方位显示消息提示，例如操作成功、警告或错误通知
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addBook, listBooks } from '@/api/book'

const dialogVisible = ref(false)// 控制弹窗是否显示的开关，初始是关闭的
const formRef = ref()
const submitting = ref(false)
const books = ref([])
const tableLoading = ref(false)

// form是一个响应式对象，里面存着用户输入的书名和作者
const form = reactive({
  title: '',
  author: '',
  isbn: '',
  description: '',
  publisher: '',
  publishDate: '',
  price: ''
})

/*
trigger: 'blur' 的意思是：
这个校验规则会在输入框失去焦点时触发。
也就是用户在字段里输入完内容，光标离开该输入框（比如点击到别处）时，才执行一次验证。
*/
const rules = {
  title: [
    { required: true, message: '请输入书名', trigger: 'blur' },
    { min: 1, max: 255, message: '书名长度应为1到255个字符', trigger: 'blur' }
  ],
  author: [
    { required: true, message: '请输入作者', trigger: 'blur' },
    { min: 1, max: 100, message: '作者长度应为1到100个字符', trigger: 'blur' }
  ],
  isbn: [
    { required: true, message: '请输入ISBN编号', trigger: 'blur' },
    { pattern: /^\d{13}$/, message: 'ISBN编号应为13位', trigger: 'blur' }
  ],
  description: [
    { message: '请输入简介', trigger: 'blur'},
    { min: 1, max: 255, message: '简介长度应为1到255个字符', trigger: 'blur' }
  ],
  publisher: [
    { required: true, message: '请输入出版社', trigger: 'blur' },
    { min: 1, max: 100, message: '出版社长度应为1到100个字符', trigger: 'blur' }
  ],
  publishDate: [
    { required: true, message: '请输入出版日期', trigger: 'blur' },
    { pattern: /^\d{4}-\d{2}-\d{2}$/, message: '出版日期格式应为YYYY-MM-DD', trigger: 'change'}
  ],
  price: [
    { required: true, message: '请输入定价', trigger: 'blur' },
    { type: 'number', min: 0.01, message: '定价必须在0.01元以上', trigger: 'blur' }
  ]
}

// 弹出对话框
function openDialog() {
  dialogVisible.value = true
}

// 清空表单
function resetForm() {
  form.title = ''
  form.author = ''
  form.isbn = ''
  form.description = ''
  form.publisher = ''
  form.publishDate = ''
  form.price = ''
  formRef.value?.clearValidate()
}

// 处理取消操作
function handleCancel() {
  dialogVisible.value = false
  resetForm()
}

function normalizeBook(raw = {}) {
  return {
    title: raw.title,
    author: raw.author,
    isbn: raw.isbn,
    description: raw.description,
    publisher: raw.publisher,
    publishDate: raw.publishDate,
    price: raw.price
  }
}

async function fetchBooks() {
  try {
    tableLoading.value = true
    const res = await listBooks()
    const list = Array.isArray(res) ? res : Array.isArray(res?.data) ? res.data : []
    books.value = list.map(normalizeBook)
  } catch (error) {
    ElMessage.error(`图书列表加载失败：${error.message || '请稍后重试'}`)
  } finally {
    tableLoading.value = false
  }
}

onMounted(() => {
  fetchBooks()
})

function handleEdit(row) {
  ElMessage.info(`编辑功能开发中：${row.title || row.bookName || '未命名图书'}`)
}

function handleDelete(row) {
  ElMessage.info(`删除功能开发中：${row.title || row.bookName || '未命名图书'}`)
}

// 处理确认操作：async用于声明异步函数，让函数内部可以使用await来等待Promise完成
async function handleConfirm() {

  // 检查表单引用是否存在，并防止重复提交
  if (!formRef.value || submitting.value) return

  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  // 构建数据载荷，将表单数据整理成符合 API 接口要求的对象格式
  const payload = {
    title: form.title,
    author: form.author,
    isbn: form.isbn,
    description: form.description,
    publisher: form.publisher,
    publishDate: form.publishDate,
    price: Number(form.price)
  }

  try {
    submitting.value = true
    await addBook(payload)// 使用await等待addBook(payload)这一异步操作完成
    await fetchBooks()

    ElMessage.success(`已新增图书：${form.title}`)
    dialogVisible.value = false
    resetForm()
  } catch (error) {
    ElMessage.error(`新增失败：${error.message || '请稍后重试'}`)
  } finally {
    submitting.value = false
  }
}
</script>

<template>
  <section class="book-page">
    <header class="toolbar">
      <h1 class="title">Zsh-Library</h1>
      <div class="toolbar-actions">
        <el-button type="primary" @click="openDialog">新增图书</el-button>
      </div>
    </header>

    <el-dialog v-model="dialogVisible" title="新增图书" width="460px" @closed="resetForm">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" status-icon>
        <el-form-item label="书名" prop="title">
          <el-input v-model="form.title" clearable />
        </el-form-item>

        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" clearable />
        </el-form-item>
        
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="form.isbn" clearable placeholder="请输入13位ISBN编号" />
        </el-form-item>
        
        <el-form-item label="简介" prop="description">
          <el-input 
            v-model="form.description" 
            type="textarea" 
            :rows="3"
            clearable 
          />
        </el-form-item>
        
        <el-form-item label="出版社" prop="publisher">
          <el-input v-model="form.publisher" clearable />
        </el-form-item>
        
        <el-form-item label="出版日期" prop="publishDate">
          <el-date-picker
            v-model="form.publishDate"
            type="date"
            placeholder="选择出版日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
        </el-form-item>
        
        <el-form-item label="定价" prop="price">
          <el-input 
            v-model.number="form.price" 
            type="number" 
            :min="0.01"
            step="1.00"
            clearable
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button :disabled="submitting" @click="handleCancel">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleConfirm">确认</el-button>
      </template>
    </el-dialog>

    <section class="book-table-wrap">
      <el-table v-loading="tableLoading" :data="books" border stripe style="width: 100%">
        <el-table-column prop="title" label="书名" min-width="140" />
        <el-table-column prop="author" label="标题" min-width="140" />
        <el-table-column prop="isbn" label="ISBN" min-width="160" />
        <el-table-column prop="description" label="简介" min-width="220" show-overflow-tooltip />
        <el-table-column prop="publisher" label="出版社" min-width="140" />
        <el-table-column prop="publishDate" label="出版日期" min-width="130" />
        <el-table-column prop="price" label="定价" min-width="110" align="right" header-align="center">
          <template #default="scope">
            {{ Number(scope.row.price || 0).toFixed(2) }} 元
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" align="center" fixed="right">
          <template #default="scope">
            <el-button type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </section>
  </section>
</template>

<style scoped>
.book-page {
  max-width: 1200px;
  margin: 36px auto;
  padding: 0 18px;
}

.toolbar {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 30px;
}

.toolbar-actions {
  display: flex;
  justify-content: flex-start;
}

.title {
  margin: 0;
  font-size: 40px;
  line-height: 1.2;
  color: #1f2937;
  text-align: center;
}

.book-table-wrap {
  margin-top: 28px;
}
</style>
