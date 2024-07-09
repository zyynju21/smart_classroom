<template>
  <div class="edited-table-box">
    <a-row>
      <a-col :span="24" class="table-title">{{ title }}</a-col>
    </a-row>
    <a-row>
      <a-col :span="24">
        <slot name="header"></slot>
      </a-col>
      <a-col :span="24">
        <div class="table-main">
          <a-table :columns="tableColData" :data-source="tableRowData" bordered :pagination="false">
            <template slot="manufacturedDate" slot-scope="manufacturedDate">
              <a-date-picker :value="manufacturedDate" format="YYYY-MM-DD" disabled />
            </template>
            <template slot="expiredDate" slot-scope="expiredDate">
              <a-date-picker :value="expiredDate" format="YYYY-MM-DD" disabled />
            </template>
            <template slot="operation" slot-scope="text, record">
              <div class="editable-row-operations">
                <span>
                  <a-button
                    :disabled="isView"
                    type="link"
                    size="small"
                    @click="() => handleEditTableRow(record.key)"
                    class="blue-green-text"
                  >
                    <a-icon type="edit" />
                  </a-button>
                </span>
                <a-divider type="vertical" />
                <span>
                  <a-popconfirm
                    :disabled="isView||record.isVisible===true"
                    title="Sure to Delete?"
                    @confirm="() => handleDeleteTableRow(record.key)"
                  >
                    <a-button class="blue-green-text" size="small" type="link" :disabled="isView||record.isVisible===true">
                      <a-icon type="delete" style="color: red" v-if="!isView && record.isVisible!==true"/>
                      <a-icon type="delete" v-if="isView || record.isVisible===true"/>
                    </a-button>
                  </a-popconfirm>
                </span>
              </div>
            </template>
          </a-table>
        </div>
      </a-col>
      <a-col :span="24">
        <slot name="footer"></slot>
      </a-col>
    </a-row>
    <a-modal
      v-model="visible"
      width="50vW"
      :bodyStyle="{padding:'10px 80px 10px 10px',margin:'auto'}"
      :title="title"
      :destroyOnClose="true">
      <a-row>
        <a-col class="modal-box">
          <slot name="modalContent"></slot>
        </a-col>
      </a-row>
      <template slot="footer">
        <a-button key="back" @click="handleCancel">{{ $t('button.cancel') }}</a-button>
        <a-button key="submit" type="primary" @click="handleModalOk">{{ $t('button.confirm') }}</a-button>
      </template>
    </a-modal>
  </div>
</template>
<script>
import BaseForm from '../about_form/BaseForm'
export default {
  props: {
    title: {
      type: String,
      default: ''
    },
    // 表格初始数据
    tableRowData: {
      type: Array,
      default: () => {
        return []
      }
    },
    // 表头信息
    tableColData: {
      type: Array,
      default: () => {
        return []
      }
    },
    isView: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      visible: false // 表单弹出框显示flag
    }
  },
  components: {
    BaseForm
  },
  methods: {
    // 单元格添加行
    handleAddRow () {
      this.visible = true
    },
    handleEditTableRow (key) {
      this.$emit('handleEditTableRow', key)
      this.visible = true
    },
    handleModalOk () {
      this.$emit('handleModalOk')
    },
    handleCancel () {
      this.visible = false
    },
    // 删除行
    handleDeleteTableRow (key) {
      this.$emit('handleDeleteTableRow', key)
    }
  }
}
</script>
<style lang="less" scoped>
@import url('editableTable.less');
</style>
