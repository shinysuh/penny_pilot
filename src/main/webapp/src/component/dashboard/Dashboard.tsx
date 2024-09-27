import * as React from 'react';

import {
    chartsCustomizations,
    dataGridCustomizations,
    datePickersCustomizations,
    treeViewCustomizations,
} from './templateTheme/customizations';
import AppTheme from "../sahred-template-theme/AppTheme.tsx";

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
                <></>
            </AppTheme>
            
        </React.Fragment>
    );
};

export default Dashboard;
