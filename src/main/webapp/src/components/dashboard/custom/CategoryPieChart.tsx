import * as React from 'react'
import Card from '@mui/material/Card'
import CardContent from '@mui/material/CardContent'
import Chip from '@mui/material/Chip'
import Typography from '@mui/material/Typography'
import Stack from '@mui/material/Stack'
import { styled } from '@mui/material/styles'
import { PieChart } from '@mui/x-charts/PieChart'
import { useDrawingArea } from '@mui/x-charts/hooks'
import {
  TransactionPeriodType,
  TransactionType,
} from '../../../types/TransactionType.ts'
import Grid from '@mui/material/Grid2'
import { useGetCtgTotalByPeriodMutation } from '../../../services/TransactionService.ts'
import {
  IPeriodParam,
  ITotalByCtg,
  ITotalByPeriod,
} from '../../../interfaces/TransactionInterface.ts'
import { compactNumberFormatter } from '../../../utils/NumberFormatter.ts'

type CategoryPieChartProperties = {
  periodParams: IPeriodParam
}

const CategoryPieChart = (props: CategoryPieChartProperties) => {
  interface StyledTextProps {
    variant: 'primary' | 'secondary'
  }

  const StyledText = styled('text', {
    shouldForwardProp: (prop) => prop !== 'variant',
  })<StyledTextProps>(({ theme }) => ({
    textAnchor: 'middle',
    dominantBaseline: 'central',
    fill: (theme.vars || theme).palette.text.secondary,
    variants: [
      {
        props: {
          variant: 'primary',
        },
        style: {
          fontSize: theme.typography.h5.fontSize,
        },
      },
      {
        props: ({ variant }) => variant !== 'primary',
        style: {
          fontSize: theme.typography.body2.fontSize,
        },
      },
      {
        props: {
          variant: 'primary',
        },
        style: {
          fontWeight: theme.typography.h5.fontWeight,
        },
      },
      {
        props: ({ variant }) => variant !== 'primary',
        style: {
          fontWeight: theme.typography.body2.fontWeight,
        },
      },
    ],
  }))

  interface PieCenterLabelProps {
    primaryText: string
    secondaryText: string
  }

  function PieCenterLabel({ primaryText, secondaryText }: PieCenterLabelProps) {
    const { width, height, left, top } = useDrawingArea()
    const primaryY = top + height / 2 - 10
    const secondaryY = primaryY + 24

    return (
      <React.Fragment>
        <StyledText variant="primary" x={left + width / 2} y={primaryY}>
          {primaryText}
        </StyledText>
        <StyledText variant="secondary" x={left + width / 2} y={secondaryY}>
          {secondaryText}
        </StyledText>
      </React.Fragment>
    )
  }

  const [totalByTransactionType, setTotalByTransactionType] =
    React.useState<ITotalByPeriod>({
      transactionPeriod: '',
      transactions: [],
    })

  const [getCtgTotalByPeriod] = useGetCtgTotalByPeriodMutation()

  React.useEffect(() => {
    getCtgTotalByPeriod(props.periodParams)
      .unwrap()
      .then((ctgTotal) => {
        if (!!ctgTotal) setTotalByTransactionType(ctgTotal)
      })
  }, [props.periodParams])

  const getColors = (type: string) => {
    const lightness = 50

    return type === TransactionType.INCOME
      ? [
          `hsl(212,${lightness}%,65%)`,
          `hsl(205,${lightness}%,42%)`,
          `hsl(210,${lightness}%,35%)`,
          `hsl(203,${lightness}%,25%)`,
        ]
      : type === TransactionType.EXPENSE
        ? [
            `hsl(0,${lightness}%,65%)`,
            `hsl(0,${lightness}%,42%)`,
            `hsl(0,${lightness}%,35%)`,
            `hsl(0,${lightness}%,25%)`,
          ]
        : [
            'hsl(220, 20%, 65%)',
            'hsl(220, 20%, 42%)',
            'hsl(220, 20%, 35%)',
            'hsl(220, 20%, 25%)',
          ]
  }

  const getPieChartByTransactionType = React.useCallback(
    (type: string): React.JSX.Element => {
      let total: string

      const target: ITotalByCtg[] =
        totalByTransactionType.transactions.find((tra) => {
          total = compactNumberFormatter.format(tra.totalAmount) || '0'
          return tra.transactionType === type
        })?.ctgTotals || []

      const data = target.map((ctgTotal) => {
        return {
          label: ctgTotal.ctgNm,
          value: ctgTotal.totalAmount,
        }
      })

      return (
        <Grid size={{ md: 6 }}>
          <PieChart
            colors={getColors(type)}
            margin={{
              left: 0,
              right: 0,
              top: 40,
              bottom: 40,
            }}
            series={[
              {
                data,
                innerRadius: 60,
                outerRadius: 90,
                paddingAngle: 0,
                highlightScope: { faded: 'global', highlighted: 'item' },
              },
            ]}
            height={265}
            width={215}
            slotProps={{
              legend: { hidden: true },
            }}
          >
            <PieCenterLabel
              primaryText={total}
              secondaryText={type.toUpperCase()}
            />
          </PieChart>
        </Grid>
      )
    },
    [totalByTransactionType],
  )

  const getPeriodToCompare = React.useCallback((): string => {
    switch (props.periodParams.periodType) {
      case TransactionPeriodType.YEAR:
        return '전년'
      case TransactionPeriodType.MONTH:
        return '전월'
      case TransactionPeriodType.DAY:
        return '전일'
      default:
        return '전월'
    }
  }, [props.periodParams.periodType])

  return (
    <Card variant="outlined" sx={{ width: '100%' }}>
      <CardContent>
        <Typography component="h2" variant="subtitle2" gutterBottom>
          Categories
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
            <Typography variant="h5" component="p">
              {getPeriodToCompare()} 대비
            </Typography>
            {/*TODO - 총합 전기간 대비 -빨강 +초록*/}
            <Chip size="small" color="error" label="-8%" />
          </Stack>
          <Typography variant="caption" sx={{ color: 'text.secondary' }}>
            Proportion by Category
          </Typography>
        </Stack>
        <Grid container size={{ md: 12 }}>
          {getPieChartByTransactionType(TransactionType.INCOME)}
          {getPieChartByTransactionType(TransactionType.EXPENSE)}
        </Grid>
      </CardContent>
    </Card>
  )
}

export default CategoryPieChart
