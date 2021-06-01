import Layout from '@/layout'

const companyRouter = {
  path: '/company',
  component: Layout,
  redirect: '/company/index',
  name: 'company',
  meta: {
    title: '公司列表',
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
