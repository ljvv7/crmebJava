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
      component: () => import('@/views/crane/addCrane'),
      name: 'addCrane',
      meta: { title: '车辆新增', icon: '' }
    },
    {
      path: 'addWeight',
      component: () => import('@/views/crane/addWeight'),
      name: 'addWeight',
      meta: { title: '配重新增', icon: '' }
    },
  ]
}

export default craneRouter
