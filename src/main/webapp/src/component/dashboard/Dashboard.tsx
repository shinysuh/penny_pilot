import * as React from 'react';
import AppTheme from '../sahred-template-theme/AppTheme.tsx';
import CssBaseline from '@mui/material/CssBaseline';
import { alpha } from '@mui/material/styles';
import {
  chartsCustomizations,
  dataGridCustomizations,
  datePickersCustomizations,
  treeViewCustomizations,
} from './templateTheme/customizations';
import SideMenu from './components/SideMenu.tsx';
import AppNavbar from './components/AppNavbar.tsx';
import Box from '@mui/material/Box';
import Header from './components/Header.tsx';
import MainGrid from './components/MainGrid.tsx';
import Stack from '@mui/material/Stack';

const themeComponents = {
  ...chartsCustomizations,
  ...dataGridCustomizations,
  ...datePickersCustomizations,
  ...treeViewCustomizations,
};

const Dashboard = () => {
  return (
    <React.Fragment>
      <AppTheme disableCustomTheme={false} themeComponents={themeComponents}>
        <CssBaseline enableColorScheme />
        <Box sx={{ display: 'flex' }}>
          <SideMenu />
          <AppNavbar />
          {/* Main content */}
          <Box
            component="main"
            sx={(theme) => ({
              flexGrow: 1,
              backgroundColor: theme.vars
                ? `rgba(${theme.vars.palette.background.defaultChannel} / 1)`
                : alpha(theme.palette.background.default, 1),
              overflow: 'auto',
            })}
          >
            <Stack
              spacing={2}
              sx={{
                alignItems: 'center',
                mx: 3,
                pb: 10,
                mt: { xs: 8, md: 0 },
              }}
            >
              <Header />
              <MainGrid />
            </Stack>
          </Box>
        </Box>
      </AppTheme>
    </React.Fragment>
  );
};

export default Dashboard;
