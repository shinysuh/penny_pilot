import * as React from 'react';
import { Route, Routes } from 'react-router-dom';
import DashboardTemplate from './templates/dashboard/DashboardTemplate.tsx';
import Dashboard from './component/dashboard/Dashboard.tsx';

type AppRouteProps = {
  showDashboard: boolean;
};

const AppRoutes = (props: AppRouteProps) => {
  return (
    <Routes>
      {!props.showDashboard && (
        <Route
          path={'/'}
          element={<DashboardTemplate disableCustomTheme={true} />}
        />
      )}
      {props.showDashboard && <Route path={'/'} element={<Dashboard />} />}

      {/*<Route path="/" element={<Home />} />*/}
      {/*<Route path="/about" element={<About />} />*/}
      {/*<Route path="/contact" element={<Contact />} />*/}
    </Routes>
  );
};
export default AppRoutes;
