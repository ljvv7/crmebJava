import Layout from '@/layout'

const craneRouter = {
  path: '/crane',
  component: Layout,
  redirect: '/crane/index',
  name: 'crane',
  meta: {
    title: '车辆管理',
    icon: 'clipboard'
  },
  children: [
    {
      path: 'craneQuery',
      component: () => import('@/views/crane/index'),
      name: 'index',
      meta: { title: '车辆列表', icon: '' }
    },
    {
      path: 'craneDetails',
      component: () => import('@/views/crane/detail'),
      name: 'detail',
      meta: { title: '车辆详情', icon: '' }
    },
    {
      path: 'addCrane',
      component: () => import('@/views/crane/add'),
      name: 'add',
      meta: { title: '车辆新增', icon: '' }
    },
  ]
}

export default craneRouter
