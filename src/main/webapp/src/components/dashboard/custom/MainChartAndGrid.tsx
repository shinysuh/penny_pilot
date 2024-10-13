import * as React from 'react'
import Grid from '@mui/material/Grid2'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Copyright from '../internals/components/Copyright'
import AccumulativeChart from './AccumulativeChart.tsx'
import {
  IDailyTransaction,
  IMonthlyTransaction,
  IPeriodParam,
} from '../../../interfaces/TransactionInterface.ts'
import { useGetMonthlyTransactionsMutation } from '../../../services/TransactionService.ts'
import { getDaysInMonth } from '../../../utils/DateTimeData.ts'
import TopChartItem, { StatCardProps } from './TopChartItem.tsx'
import MonthlyTransactionDataGrid from './MonthlyTransactionDataGrid.tsx'
import { compactNumberFormatter } from '../../../utils/NumberFormatter.ts'
import { IUser } from '../../../interfaces/UserInterface.ts'
import CategoryPieChart from './CategoryPieChart.tsx'

type MainChartAndGridProperties = {
  user: IUser
  targetPeriod: string
}

const MainChartAndGrid = (props: MainChartAndGridProperties) => {
  const [displayData, setDisplayData] = React.useState<StatCardProps[]>([])
  const [monthlyTransaction, setMonthlyTransaction] =
    React.useState<IMonthlyTransaction>({
      transactionMonth: '',
      totalIncome: 0,
      totalExpense: 0,
      transactionByDays: [],
    })

  const setMonthlyValues = (
    monthlyData: IMonthlyTransaction,
    dayCount: number,
  ): void => {
    // 0 arrays
    let incomes: number[] = new Array(dayCount).fill(0)
    let expenses: number[] = new Array(dayCount).fill(0)
    let traCounts: number[] = new Array(dayCount).fill(0)

    let target: IDailyTransaction[] = monthlyData.transactionByDays.filter(
      (tra) => tra.totalIncome > 0 || tra.totalExpense > 0,
    )

    for (let tra of target) {
      const idx: number = parseInt(tra.day.slice(-2)) - 1
      incomes[idx] = tra.totalIncome
      expenses[idx] = tra.totalExpense
      traCounts[idx] = tra.transactions.length
    }

    const data: StatCardProps[] = [
      {
        title: 'Income',
        value: compactNumberFormatter.format(monthlyData.totalIncome),
        interval: `Last ${dayCount} days`,
        trend: 'up',
        data: incomes,
      },
      {
        title: 'Expenses',
        value: compactNumberFormatter.format(monthlyData.totalExpense),
        interval: `Last ${dayCount} days`,
        trend: 'down',
        data: expenses,
      },
    ]

    setDisplayData([...data])
  }

  const [getMonthlyTransactions] = useGetMonthlyTransactionsMutation()

  React.useEffect(() => {
    const params: IPeriodParam = {
      userId: props.user.id,
      transactionPeriod: props.targetPeriod,
    }

    getMonthlyTransactions(params)
      .unwrap()
      .then((monthlyData) => {
        if (!!monthlyData) {
          const daysInMonth = getDaysInMonth(props.targetPeriod)
          setMonthlyTransaction(monthlyData)
          setMonthlyValues(monthlyData, daysInMonth)
        }
      })
  }, [props])

  return (
    <Box sx={{ width: '100%', maxWidth: { sm: '100%', md: '1700px' } }}>
      {/* cards */}
      <Typography component="h2" variant="h6" sx={{ mb: 2 }}>
        Overview
      </Typography>
      <Grid
        container
        spacing={2}
        columns={12}
        sx={{ mb: (theme) => theme.spacing(2) }}
      >
        <Grid size={{ md: 12, lg: 6 }}>
          <MonthlyTransactionDataGrid
            user={props.user}
            monthlyTRA={monthlyTransaction}
          />
        </Grid>
        <Grid container size={{ md: 12, lg: 6 }}>
          {displayData.map((card, index) => (
            <Grid key={index} size={{ xs: 12, sm: 6 }}>
              <TopChartItem card={card} targetPeriod={props.targetPeriod} />
            </Grid>
          ))}
          <Grid size={{ sm: 12, md: 12 }}>
            <CategoryPieChart />
          </Grid>
          <Grid size={{ sm: 12, md: 12 }}>
            <AccumulativeChart
              userId={props.user.id}
              targetPeriod={props.targetPeriod}
              monthlyTransaction={monthlyTransaction}
              topChartData={displayData}
            />
          </Grid>
        </Grid>
      </Grid>
      <Copyright sx={{ my: 4 }} />
    </Box>
  )
}

export default MainChartAndGrid
