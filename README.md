# Quantitative Trading Strategy Using Yahoo Finance Data  

## 📌 Overview  
This project develops and analyzes a **quantitative trading strategy** using historical US equity price data, fetched via **Yahoo Finance’s API**.  
The strategy focuses on **moving average crossovers** and evaluates performance using key financial metrics.  
The workflow enables experimentation and showcases techniques for **risk management and optimization**.  

---

## 🎯 Problem Statement  
- Fetches historical **adjusted closing prices** for multiple US equities, ensuring **broad sector coverage**.  
- Computes **daily returns**, ignoring trading costs and slippage at this stage.  
- Constructs an **unbalanced panel dataset** for testing.  
- Selects firms and industries for **diverse market representation**.  

---

## 📊 Data Extraction  
- Utilizes the **`yfinance`** Python library to download daily stock price data.  
- Focuses on **adjusted close prices** (accounts for splits and dividends).  
- Computes **daily returns**:  

\[
Return_t = \frac{P_t - P_{t-1}}{P_{t-1}}
\]

where \( P_t \) is the adjusted close price at time \( t \).  

- Handles **missing data points** inherent to unbalanced panels.  

---

## 📈 Trading Strategy  
Implements a **moving average crossover** approach:  
- **Short-Term Moving Average (SMA)**  
- **Long-Term Moving Average (LMA)**  

**Signals:**  
- ✅ **Buy** → Short-term MA crosses above long-term MA (upward momentum).  
- ❌ **Sell** → Short-term MA crosses below long-term MA (downward momentum).  

Applied across all selected stocks, managing **long and short positions** accordingly.  

---

## 📉 Performance Metrics  
- **Average Return** → Mean of daily portfolio returns.  
- **Volatility** → Standard deviation of portfolio returns.  
- **Sharpe Ratio** → Risk-adjusted return, accounting for volatility and a risk-free rate.  
- **Maximum Drawdown** → Largest drop from peak to trough.  
- **Signal Accuracy** → % of profitable trades from strategy signals.  
- **Beta Estimation** → Regression of strategy returns against market returns.  

---

## 🔍 Observations  
- Strategy’s **cumulative returns consistently outperformed** the market benchmark.  
- **Average return, volatility, and max drawdown** were strong indicators.  
- **Sharpe ratio** was modest, showing limited risk-adjusted performance.  

---

## 🔧 Strategy Improvements  
- **Window Tuning** → Tested different moving average lengths.  
- **Exponentially Weighted Averages (EWA)** →  
  - More weight on recent data → reduced lag.  
  - Slightly improved Sharpe ratio but introduced **whipsaws** in sideways markets.  
- **Stock Weighting by Volatility** → Decreased Sharpe ratio unexpectedly.  

📌 **Key Insight**: EWA improved responsiveness but could **overreact** in certain markets, leading to higher trading costs.  

---

## ✅ Conclusion  
- Both **technical indicators** and **risk metrics** provide insights.  
- The **choice of moving average (SMA vs. EWA)** did not dramatically affect risk-adjusted returns.  
- **Sharpe ratio** depended more on **stock selection** and **market conditions**.  
- Future improvements could include:  
  - Longer-term moving averages  
  - Value-based stock selection  
  - Incorporation of **transaction costs & slippage**  

---

## 📂 Project Structure  
├── data/ # Historical stock price data storage
├── notebooks/ # Jupyter notebooks for analysis & visualization
├── src/ # Core scripts
│ ├── data_fetch.py # Yahoo Finance extraction
│ ├── strategy.py # Trading logic
│ ├── metrics.py # Performance metrics
├── results/ # Output plots & analytics
├── README.md # Project documentation

yaml
Copy code

---

## ⚙️ Installation & Usage  

### 1️⃣ Clone the repository  
```bash
git clone https://github.com/your-username/quant-trading-strategy.git
cd quant-trading-strategy
2️⃣ Install dependencies
bash
Copy code
pip install -r requirements.txt
3️⃣ Run the strategy
bash
Copy code
python src/strategy.py
4️⃣ Explore with Jupyter Notebook
bash
Copy code
jupyter notebook notebooks/
