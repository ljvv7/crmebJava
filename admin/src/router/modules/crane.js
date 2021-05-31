import Layout from '@/layout'

const craneRouter = {
  path: '/cranePro',
  component: Layout,
  redirect: '/crane/index',
  name: 'cranePro',
  meta: {
    title: '吊臂项目',
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
