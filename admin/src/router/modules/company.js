import Layout from '@/layout'

const companyRouter = {
  path: '/craneManage',
  component: Layout,
  redirect: '/company/index',
  name: 'CraneManage',
  meta: {
    title: '公司管理',
    icon: 'clipboard'
  },
  children: [
    {
      path: 'craneQuery',
      component: () => import('@/views/company/index'),
      name: 'StoreIndex',
      meta: { title: '公司列表', icon: '' }
    }
  ]
}

export default companyRouter
