import Layout from '@/layout'

const companyRouter = {
  path: '/cranePro',
  component: Layout,
  redirect: '/company/index',
  name: 'cranePro',
  meta: {
    title: '吊臂项目',
    icon: 'clipboard'
  },
  children: [
    {
      path: 'companyQuery',
      component: () => import('@/views/company/index'),
      name: 'CompanyIndex',
      meta: { title: '公司列表', icon: '' }
    }
  ]
}

export default companyRouter
