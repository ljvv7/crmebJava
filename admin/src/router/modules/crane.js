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
      name: 'CraneIndex',
      meta: { title: '车辆列表', icon: '' }
    }
  ]
}

export default craneRouter
