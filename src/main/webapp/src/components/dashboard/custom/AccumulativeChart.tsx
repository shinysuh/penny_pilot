import * as React from 'react'
import { useTheme } from '@mui/material/styles'
import Card from '@mui/material/Card'
import CardContent from '@mui/material/CardContent'
import Chip from '@mui/material/Chip'
import Typography from '@mui/material/Typography'
import Stack from '@mui/material/Stack'
import { LineChart } from '@mui/x-charts/LineChart'
import { IMonthlyTransaction } from '../../../interfaces/TransactionInterface.ts'
import { LineSeriesType } from '@mui/x-charts/models/seriesType/line'
import { MakeOptional } from '@mui/x-charts/internals'
import { StatCardProps } from './TopChartItem.tsx'
import { getDaysAndMonth } from '../../../utils/DateTimeData.ts'

type AccumulativeChartProperties = {
  userId: number
  targetPeriod: string
  monthlyTransaction: IMonthlyTransaction
  topChartData: StatCardProps[]
}

const AccumulativeChart = (props: AccumulativeChartProperties) => {
  const theme = useTheme()
  const title = 'Accumulations'

  const [daysAndMonth, setDaysAndMonth] = React.useState<string[]>([])
  const [series, setSeries] = React.useState<
    MakeOptional<LineSeriesType, 'type'>[]
  >([])

  const colorPalette = [
    theme.palette.primary.light,
    theme.palette.primary.main,
    theme.palette.primary.dark,
  ]

  const getAreaGradient = (color: string, id: string) => {
    return (
      <linearGradient id={id} x1="50%" y1="0%" x2="50%" y2="100%">
        <stop offset="0%" stopColor={color} stopOpacity={0.5} />
        <stop offset="100%" stopColor={color} stopOpacity={0} />
      </linearGradient>
    )
  }

  const getCumulatedData = (data: number[]) => {
    let result: number[] = []
    let cumulation = 0

    for (let i = 0; i < data.length; i++) {
      cumulation += data[i]
      result[i] = cumulation
    }

    return result
  }

  React.useEffect(() => {
    setDaysAndMonth(getDaysAndMonth(props.targetPeriod))
  }, [props.targetPeriod])

  React.useEffect(() => {
    setSeries(
      props.topChartData.map((data, index) => {
        return {
          id: data.title,
          label: data.title,
          showMark: false,
          curve: 'linear',
          stack: 'total',
          area: true,
          stackOrder: 'ascending',
          data: getCumulatedData(data.data),
        }
      }),
    )
  }, [props.topChartData])

  return (
    <Card variant="outlined" sx={{ width: '100%' }}>
      <CardContent>
        <Typography component="h2" variant="subtitle2" gutterBottom>
          {title}
        </Typography>
        <Stack sx={{ justifyContent: 'space-between' }}>
          <Stack
            direction="row"
            sx={{
              alignContent: { xs: 'center', sm: 'flex-start' },
              alignItems: 'center',
              gap: 1,
            }}
          >
            <Typography variant="h4" component="p">
              13,277
            </Typography>
            <Chip size="small" color="success" label="+35%" />
          </Stack>
          <Typography variant="caption" sx={{ color: 'text.secondary' }}>
            {title} per day for the last {daysAndMonth.length} days
          </Typography>
        </Stack>
        {!!props.monthlyTransaction.transactionMonth && (
          <LineChart
            colors={colorPalette}
            xAxis={[
              {
                scaleType: 'point',
                data: daysAndMonth,
                tickInterval: (index, i) => (i + 1) % 5 === 0,
              },
            ]}
            series={series}
            height={250}
            margin={{ left: 50, right: 20, top: 20, bottom: 20 }}
            grid={{ horizontal: true }}
            sx={{
              '& .MuiAreaElement-series-organic': {
                fill: "url('#organic')",
              },
              '& .MuiAreaElement-series-referral': {
                fill: "url('#referral')",
              },
              '& .MuiAreaElement-series-direct': {
                fill: "url('#direct')",
              },
            }}
            slotProps={{
              legend: {
                hidden: true,
              },
            }}
          >
            {getAreaGradient(theme.palette.primary.dark, 'organic')}
            {getAreaGradient(theme.palette.primary.main, 'referral')}
            {getAreaGradient(theme.palette.primary.light, 'direct')}
          </LineChart>
        )}
      </CardContent>
    </Card>
  )
}

export default AccumulativeChart
