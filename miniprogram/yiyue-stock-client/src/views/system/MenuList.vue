<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <a-card style="margin-top: 0px;" :bordered="false">
      <a-button type="primary" @click="handleAddMenu" style="margin-bottom: 8px;">
        <a-icon type="plus" />
        {{ $t('button.add') }}
      </a-button>
    </a-card>
    <a-row>
      <a-col>
        <a-table
          :loading="loading"
          :columns="menuColumns"
          :data-source="sysMenuList"
          :rowKey="record => record.menuId"
          :pagination="false"
          :defaultExpandAllRows="true"
          v-if="sysMenuList.length>0">
          <!-- <span slot="menuState" slot-scope="menuState">
            <a-tag v-if="menuState === 0" color="green">{{ $t('sysMenu.menu_state_enable') }}</a-tag>
            <a-tag v-if="menuState === -1" color="gray">{{ $t('sysMenu.menu_state_disable') }}</a-tag>
          </span> -->
          <span slot="menuIcon" slot-scope="menuIcon">
            <a-icon :type="menuIcon" v-if="menuIcon !== null && menuIcon !==''" />
          </span>
          <template slot="action" slot-scope="text, record">
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateMenu(record.menuId)" class="blue-green-text"/>
            </a>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteMenu(record.menuId)" class="blue-green-text"/>
            </a>
          </template>
        </a-table>
      </a-col>
    </a-row>
  </div>
</template>

<script>

  import BreadCrumb from '@/components/tools/Breadcrumb'
  import { getSystemMenuList, deleteSystemMenu } from '../../api/sys/sys_menu.js'

  export default {
    components: {
      BreadCrumb
    },
    data () {
      return {
        sysMenuList: [],
        loading: false
      }
    },
    computed: {
      menuColumns () {
        return [{
            title: this.$t('sysMenu.menu_name'),
            dataIndex: 'menuName',
            align: 'left'
          },
          {
            title: this.$t('sysMenu.menu_url'),
            dataIndex: 'menuUrl',
            align: 'left'
          },
          {
            title: this.$t('sysMenu.menu_component'),
            dataIndex: 'menuComponent',
            align: 'left'
          },
          {
            title: this.$t('sysMenu.menu_icon'),
            dataIndex: 'menuIcon',
            scopedSlots: {
              customRender: 'menuIcon'
            }
          },
          /* {
            title: this.$t('sysMenu.menu_state'),
            dataIndex: 'menuState',
            scopedSlots: {
              customRender: 'menuState'
            }
          }, */
          {
            title: this.$t('sysMenu.menu_no'),
            dataIndex: 'menuNo'
          },
          {
            title: this.$t('button.operation'),
            scopedSlots: {
              customRender: 'action'
            }
          }]
      }
    },
    mounted () {
      this.loadSysMenuList()
    },
    methods: {
      loadSysMenuList () {
        this.loading = false
        getSystemMenuList().then(res => {
          // console.log(res)
          this.sysMenuList = res.data
        }).finally(() => {
          this.loading = false
        })
      },
      handleAddMenu () {
        this.$router.push({ path: '/system/menu/detail/create' })
      },
      handleUpdateMenu (menuId) {
        this.$router.push({ path: '/system/menu/detail/edit/' + menuId })
      },
      handleDeleteMenu (menuId) {
        const _this = this
        this.$confirm({
          okText: this.$t('button.confirm'),
          cancelText: this.$t('button.cancel'),
          title: this.$t('message.delete_confirm'),
          onOk () {
            // console.log('执行删除menuid' + menuId)
            _this.executeDeleteSysMenu(menuId)
          }
        })
      },
      executeDeleteSysMenu (menuId) {
        deleteSystemMenu(menuId).then(() => {
          this.$success({
            title: this.$t('message.delete_success')
          })
          this.loadSysMenuList()
        })
      }
    }
  }
</script>

<style lang="less" scoped>
  @import url('~@/assets/less/qm-erp-list.less');
</style>
