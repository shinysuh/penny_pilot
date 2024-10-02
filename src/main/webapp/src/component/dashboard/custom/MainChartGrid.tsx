import * as React from 'react'
import Grid from '@mui/material/Grid2'
import Box from '@mui/material/Box'
import Stack from '@mui/material/Stack'
import Typography from '@mui/material/Typography'
import Copyright from '../internals/components/Copyright'
import ChartUserByCountry from '../components/ChartUserByCountry'
import CustomizedTreeView from '../components/CustomizedTreeView'
import CustomizedDataGrid from '../components/CustomizedDataGrid'
import HighlightedCard from '../components/HighlightedCard'
import PageViewsBarChart from '../components/PageViewsBarChart'
import AccumulativeChart from './AccumulativeChart.tsx'
import {
  IDailyTransaction,
  IMonthlyTransaction,
  IPeriodParam,
} from '../../../interface/TransactionInterface.ts'
import { useGetMonthlyTransactionsMutation } from '../../../service/TransactionService.ts'
import { getDaysInMonth } from '../../../util/DateData.ts'
import TopChartItem, { StatCardProps } from './TopChartItem.tsx'

type TopChartsProperties = {
  userId: number
  targetPeriod: string
}

const MainChartGrid = (props: TopChartsProperties) => {
  const [displayData, setDisplayData] = React.useState<StatCardProps[]>([])
  const [monthlyTransaction, setMonthlyTransaction] =
    React.useState<IMonthlyTransaction>({
      transactionMonth: '',
      totalIncome: 0,
      totalExpense: 0,
      transactionByDays: [],
    })

  const compactNumberFormatter = new Intl.NumberFormat('ko', {
    notation: 'compact', // 축약 표기 사용
  } as Intl.NumberFormatOptions)

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
      {
        title: 'Event count',
        value: compactNumberFormatter.format(traCounts.reduce((a, b) => a + b)),
        interval: `Last ${dayCount} days`,
        trend: 'neutral',
        data: traCounts,
      },
    ]

    setDisplayData([...data])
  }

  const [getMonthlyTransactions] = useGetMonthlyTransactionsMutation()

  React.useEffect(() => {
    const params: IPeriodParam = {
      userId: props.userId,
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
        {displayData.map((card, index) => (
          <Grid key={index} size={{ xs: 12, sm: 6, lg: 3 }}>
            <TopChartItem card={card} targetPeriod={props.targetPeriod} />
          </Grid>
        ))}
        <Grid size={{ xs: 12, sm: 6, lg: 3 }}>
          <HighlightedCard />
        </Grid>
        <Grid size={{ sm: 12, md: 6 }}>
          <AccumulativeChart
            userId={props.userId}
            targetPeriod={props.targetPeriod}
            monthlyTransaction={monthlyTransaction}
            topChartData={displayData}
          />
        </Grid>
        <Grid size={{ sm: 12, md: 6 }}>
          <PageViewsBarChart />
        </Grid>
      </Grid>
      <Typography component="h2" variant="h6" sx={{ mb: 2 }}>
        Details
      </Typography>
      <Grid container spacing={2} columns={12}>
        <Grid size={{ md: 12, lg: 9 }}>
          <CustomizedDataGrid />
        </Grid>
        <Grid size={{ xs: 12, lg: 3 }}>
          <Stack gap={2} direction={{ xs: 'column', sm: 'row', lg: 'column' }}>
            <CustomizedTreeView />
            <ChartUserByCountry />
          </Stack>
        </Grid>
      </Grid>
      <Copyright sx={{ my: 4 }} />
    </Box>
  )
}

export default MainChartGrid
