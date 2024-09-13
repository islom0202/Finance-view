import sys
import json
from sklearn.linear_model import LinearRegression
import numpy as np
from datetime import datetime, timedelta

def compute_days_since_start(recorded_at, start_date):
    recorded_date = datetime.strptime(recorded_at, "%Y-%m-%dT%H:%M:%S")
    start_date = datetime.strptime(start_date, "%Y-%m-%d")
    return (recorded_date - start_date).days

def forecast_sales(data, start_date):
    if not all('value' in item and 'recordedAt' in item for item in data):
        raise ValueError("One or more items in the data are missing required keys.")

    days_since_start = [compute_days_since_start(item['recordedAt'], start_date) for item in data]
    values = [item['value'] for item in data]

    X = np.array(days_since_start).reshape(-1, 1)
    y = np.array(values)

    model = LinearRegression()
    model.fit(X, y)

    # Forecast for the next 30 days starting from today
    today = datetime.now()
    future_days = np.array(range((today - datetime.strptime(start_date, "%Y-%m-%d")).days + 1,
                                 (today - datetime.strptime(start_date, "%Y-%m-%d")).days + 31)).reshape(-1, 1)
    predictions = model.predict(future_days)

    # Compute dates for the predictions
    future_dates = [today + timedelta(days=int(day)) for day in future_days.flatten()]

    # Prepare results
    result = [{'date': date.strftime("%Y-%m-%d"), 'value': value} for date, value in zip(future_dates, predictions)]

    return json.dumps(result)  # Return JSON string

def main():
    if len(sys.argv) < 3:
        print("Insufficient arguments provided.", file=sys.stderr)
        sys.exit(1)

    json_file_path = sys.argv[1]
    start_date = sys.argv[2]

    try:
        # Read JSON data from file
        with open(json_file_path, 'r') as file:
            data = json.load(file)

        result = forecast_sales(data, start_date)
        print(result)  # Print the JSON result as a string
    except ValueError as e:
        print(str(e), file=sys.stderr)
        sys.exit(1)
    except Exception as e:
        print(f"An error occurred: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == "__main__":
    main()
#
#
# import numpy as np
# from sklearn.linear_model import LinearRegression
# from datetime import datetime, timedelta
# import json
#
# def compute_days_since_start(recorded_at, start_date):
#     recorded_date = datetime.strptime(recorded_at, "%Y-%m-%dT%H:%M:%S")
#     start_date = datetime.strptime(start_date, "%Y-%m-%d")
#     return (recorded_date - start_date).days
#
# def forecast_sales(data, start_date):
#     days_since_start = [compute_days_since_start(item['recordedAt'], start_date) for item in data]
#     values = [item['value'] for item in data]
#
#     X = np.array(days_since_start).reshape(-1, 1)
#     y = np.array(values)
#
#     model = LinearRegression()
#     model.fit(X, y)
#
#     future_days = np.array(range(X[-1][0] + 1, X[-1][0] + 31)).reshape(-1, 1)
#     predictions = model.predict(future_days)
#
#     start_date_obj = datetime.strptime(start_date, "%Y-%m-%d")
#     future_dates = [start_date_obj + timedelta(days=int(day)) for day in future_days.flatten()]
#
#     result = [{'date': date.strftime("%Y-%m-%d"), 'value': value} for date, value in zip(future_dates, predictions)]
#
#     return json.dumps(result)
