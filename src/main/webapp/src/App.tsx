import * as React from 'react';
import pennyPilotLogo from './assets/images/pennyPilotLogo_white.png'
import reactLogo from './assets/react.svg'
import './App.css'
import Dashboard from "./templates/dashboard/Dashboard.tsx";

function App() {
    const [ count, setCount ] = React.useState(0)
    
    const [ showDashboard, setShowDashboard ] = React.useState<boolean>(false)
    
    return (
        <>
            <div>
                <a href="https://vitejs.dev" target="_blank">
                    <img src={ pennyPilotLogo } className="logo" alt="Vite logo"/>
                </a>
                <a href="https://react.dev" target="_blank">
                    <img src={ reactLogo } className="logo react" alt="React logo"/>
                </a>
            </div>
            <h1>Vite + React</h1>
            <div className="card">
                <button onClick={ () => setCount((count) => count + 1) }>
                    count is { count }
                </button>
                <p>
                    Edit <code>src/App.tsx</code> and save to test HMR
                </p>
            </div>
            <p className="read-the-docs">
                Click on the Vite and React logos to learn more
            </p>
            <button onClick={ () => setShowDashboard((show) => !show) }>
                showDashboard: { showDashboard.toString() }
            </button>
            {
                showDashboard &&
                <div>
                    <Dashboard disableCustomTheme={ true }/>
                </div>
            }
        </>
    )
}

export default App
