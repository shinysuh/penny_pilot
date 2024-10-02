import * as React from 'react'
import './App.css'
import { BrowserRouter } from 'react-router-dom'
import AppRoutes from './route.tsx'

function App() {
  const [showDashboard, setShowDashboard] = React.useState<boolean>(true)
  return (
    <React.Suspense>
      <BrowserRouter>
        <button
          className={'test-button'}
          onClick={() => setShowDashboard((show) => !show)}
        >
          {showDashboard ? '대시보드' : '템플릿'}
        </button>
        <AppRoutes showDashboard={showDashboard} />
      </BrowserRouter>
    </React.Suspense>
  )
}

export default App
