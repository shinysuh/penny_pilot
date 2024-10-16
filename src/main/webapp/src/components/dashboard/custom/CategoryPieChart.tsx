import * as React from 'react'
import Card from '@mui/material/Card'
import CardContent from '@mui/material/CardContent'
import Chip from '@mui/material/Chip'
import Typography from '@mui/material/Typography'
import Stack from '@mui/material/Stack'
import { styled, useTheme } from '@mui/material/styles'
import { PieChart } from '@mui/x-charts/PieChart'
import { useDrawingArea } from '@mui/x-charts/hooks'

type CategoryPieChartProperties = {}

const CategoryPieChart = (props: CategoryPieChartProperties) => {
  const theme = useTheme()
  const colorPalette = [
    (theme.vars || theme).palette.primary.dark,
    (theme.vars || theme).palette.primary.main,
    (theme.vars || theme).palette.primary.light,
  ]

  const colors = [
    'hsl(220, 20%, 65%)',
    'hsl(220, 20%, 42%)',
    'hsl(220, 20%, 35%)',
    'hsl(220, 20%, 25%)',
  ]

  const data = [
    { label: 'India', value: 50000 },
    { label: 'USA', value: 35000 },
    { label: 'Brazil', value: 10000 },
    { label: 'Other', value: 5000 },
  ]

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
            <Typography variant="h4" component="p">
              1.3M
            </Typography>
            <Chip size="small" color="error" label="-8%" />
          </Stack>
          <Typography variant="caption" sx={{ color: 'text.secondary' }}>
            Page views and downloads for the last 6 months
          </Typography>
        </Stack>
        <PieChart
          colors={colors}
          margin={{
            left: 80,
            right: 80,
            top: 80,
            bottom: 80,
          }}
          series={[
            {
              data,
              innerRadius: 75,
              outerRadius: 100,
              paddingAngle: 0,
              highlightScope: { faded: 'global', highlighted: 'item' },
            },
          ]}
          height={260}
          width={260}
          slotProps={{
            legend: { hidden: true },
          }}
        >
          <PieCenterLabel primaryText="98.5K" secondaryText="Total" />
        </PieChart>
      </CardContent>
    </Card>
  )
}

export default CategoryPieChart
