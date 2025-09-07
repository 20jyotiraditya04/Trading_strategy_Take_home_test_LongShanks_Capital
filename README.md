Quantitative Trading Strategy Using Yahoo Finance Data
Overview
This project develops and analyzes a quantitative trading strategy using historical US equity price data, fetched via Yahoo Finance’s API. The strategy focuses on moving average crossovers and evaluates performance using key financial metrics. The workflow enables experimentation and showcases techniques for risk management and optimization.

Problem Statement
Fetches historical adjusted closing prices for multiple US equities, ensuring broad sector coverage for robust analysis.

Computes daily returns, ignoring trading costs and slippage at this stage.

Constructs an unbalanced panel dataset for further strategy testing.

Selects sample firms and industries to include diverse market representation.

Data Extraction
Utilizes the yfinance Python library to download daily price data for chosen US stocks.

Focuses on adjusted close prices (accounts for splits/dividends).

Calculates daily returns:

Return
t
=
P
t
−
P
t
−
1
P
t
−
1
Return 
t
 = 
P 
t−1
 
P 
t
 −P 
t−1
 
 
where 
P
t
P 
t
  is the adjusted close price on day 
t
t.

Handles missing data points inherent to unbalanced panels.

Trading Strategy
Implements a moving average crossover approach:

Short-Term Moving Average (SMA)

Long-Term Moving Average (LMA)

Buy Signal: Short-term MA crosses above long-term MA (upward momentum).

Sell Signal: Short-term MA crosses below long-term MA (downward momentum).

Applies signals across all selected stocks, managing long and short positions accordingly.

Performance Metrics
Average Return: Mean daily portfolio returns.

Volatility: Standard deviation of portfolio returns.

Sharpe Ratio: Risk-adjusted return, accounting for volatility and a risk-free rate.

Maximum Drawdown: Largest drop from peak to trough in portfolio value.

Signal Accuracy: Proportion of profitable trades triggered by strategy signals.

Beta Estimation: Regression of strategy returns against market returns.

Observations
The strategy’s cumulative returns curve consistently outperformed the market benchmark throughout the analysis period.

Average return, volatility, and maximum drawdown were valuable metrics, while the Sharpe ratio indicated modest risk-adjusted performance.

Strategy Improvement
Window Tuning: Experimented with different lengths for moving average windows.

Exponentially Weighted Averages (EWA):

EWA gives more weight to recent prices, reducing lag.

Observed that EWA improved Sharpe ratio slightly, but increased sensitivity resulted in more frequent trading signals (potential whipsaws).

Stock Weighting: Tried weighting positions by volatility, but it decreased Sharpe ratio—other enhancements explored.

Key Insight: EWA may overreact in sideways markets, causing higher transaction costs and lower net returns.

Conclusion
Both technical indicators and risk metrics offer insights, but the type of moving average (SMA vs. EWA) did not dramatically impact risk-adjusted returns.

The Sharpe ratio was more dependent on stock selection and market conditions than the moving average method itself.

For better results, consider experimenting with longer-term averages, value-based selection criteria, and incorporating realistic transaction costs.

Project Structure
text
├── data/                # Historical stock price data storage
├── notebooks/           # Jupyter notebooks for analysis & visualization
├── src/                 # Core scripts
│   ├── data_fetch.py    # Yahoo Finance extraction
│   ├── strategy.py      # Trading logic
│   ├── metrics.py       # Performance metrics
├── results/             # Output plots & analytics
├── README.md            # Project documentation
Installation & Usage
Clone the repository:

bash
git clone https://github.com/your-username/quant-trading-strategy.git
cd quant-trading-strategy
Install dependencies:

bash
pip install -r requirements.txt
Run the strategy:

bash
python src/strategy.py
Explore with Jupyter Notebook:

bash
jupyter notebook notebooks/
