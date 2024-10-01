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
import { StatCardProps } from './components/StatCard.tsx';
import { useGetMonthlyTransactionsMutation } from '../../service/TransactionService.ts';
import { IPeriodParam } from '../../interface/TransactionInterface.ts';
import { getCurrentDateByPeriodType } from '../../util/DateData.ts';

const themeComponents = {
  ...chartsCustomizations,
  ...dataGridCustomizations,
  ...datePickersCustomizations,
  ...treeViewCustomizations,
};

const Dashboard = () => {
  const data: StatCardProps[] = [
    {
      title: 'Income',
      value: '14k',
      interval: 'Last 30 days',
      trend: 'up',
      data: [
        200, 24, 220, 260, 240, 380, 100, 240, 280, 240, 300, 340, 320, 360,
        340, 380, 360, 400, 380, 420, 400, 640, 340, 460, 440, 480, 460, 600,
        880, 920,
      ],
    },
  ];

  const [getMonthlyTransactions] = useGetMonthlyTransactionsMutation();

  React.useEffect(() => {
    console.log('&&&&&&&&&&&&&&&&&&&&&&&', getCurrentDateByPeriodType);
    const params: IPeriodParam = {
      userId: 3, // TODO - 동적 적용
      transactionPeriod: getCurrentDateByPeriodType,
    };

    getMonthlyTransactions(params)
      .unwrap()
      .then((monthlyTra) => {
        console.log('##################### monthly result:', monthlyTra);
      });
  }, []);

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
