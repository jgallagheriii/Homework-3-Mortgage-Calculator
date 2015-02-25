package com.example.mortagecalculator;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	private static final NumberFormat doubleFormat = NumberFormat.getNumberInstance();
	private static final NumberFormat yearFormat = NumberFormat.getIntegerInstance();
	
	
	private double purchaseAmount = 0.0;
	private double downPayment = 0.0;
	private double interestRate = 0.04;
	private int customYear = 15;
	private TextView amountPurchasePrice;
	private TextView amountDownPayment;
	private TextView amountInterestRate;
	private TextView yearCustomTextView;
	private TextView monthly10TextView;
	private TextView monthly20TextView;
	private TextView monthly30TextView;
	private TextView monthlyCustomTextView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		amountPurchasePrice = (TextView) findViewById(R.id.amountDisplayPurchasePrice);
		amountDownPayment = (TextView) findViewById(R.id.displayDownPayment);
		amountInterestRate = (TextView) findViewById(R.id.displayInterestRate);
		yearCustomTextView = (TextView) findViewById(R.id.yearCustomTextView);
		monthly10TextView = (TextView) findViewById(R.id.monthly10TextView);
		monthly20TextView = (TextView) findViewById(R.id.monthly20TextView);
		monthly30TextView = (TextView) findViewById(R.id.monthly30TextView);
		monthlyCustomTextView = (TextView) findViewById(R.id.monthlyCustomTextView);
		
		amountPurchasePrice.setText(currencyFormat.format(purchaseAmount));
		amountDownPayment.setText(currencyFormat.format(downPayment));
		amountInterestRate.setText(doubleFormat.format(interestRate*100));
		//not sure how to set the interest rate so it shows up in a % format
		updateStandard();
		updateCustom();
		
		EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
		amountEditText.addTextChangedListener(amountEditTextWatcher);
		EditText downPaymentEditText = (EditText) findViewById(R.id.downPaymentEditText);
		downPaymentEditText.addTextChangedListener(downPaymentEditTextWatcher);
		EditText interestEditText = (EditText) findViewById(R.id.interestEditText);
		interestEditText.addTextChangedListener(interestEditTextWatcher);
		
		SeekBar customYearSeekBar = (SeekBar) findViewById(R.id.customYearSeekBar);
		customYearSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
		
		
		
	}
	private void updateStandard()
	{
		double loanAmount = purchaseAmount - downPayment;
		int year10toMonth = 10 *12;
		int year20toMonth = 20*12;
		int year30toMonth = 30*12;
		double interest = interestRate/12;
		
		double top10Half = loanAmount * (interest*(Math.pow(1+interest, year10toMonth)));
		double monthly10Payment = top10Half/(Math.pow(1+interest, year10toMonth)-1);
		monthly10TextView.setText(currencyFormat.format(monthly10Payment));
		
		double top20Half = loanAmount * (interest*(Math.pow(1+interest, year20toMonth)));
		double monthly20Payment = top20Half/(Math.pow(1+interest, year20toMonth)-1);
		monthly20TextView.setText(currencyFormat.format(monthly20Payment));
		
		double top30Half = loanAmount * (interest*(Math.pow(1+interest, year30toMonth)));
		double monthly30Payment = top30Half/(Math.pow(1+interest, year30toMonth)-1);
		monthly30TextView.setText(currencyFormat.format(monthly30Payment));
		
		
		
	}
	private void updateCustom()
	{
		double interest = interestRate/12;
		int yearCustomtoMonth = customYear *12;
		yearCustomTextView.setText(yearFormat.format(customYear));
		double loanAmount = purchaseAmount - downPayment;
		
		double topCustomHalf = loanAmount * (interest*(Math.pow(1+interest, yearCustomtoMonth)));
		double monthlyCustomPayment = topCustomHalf/(Math.pow(1+interest, yearCustomtoMonth)-1);
		monthlyCustomTextView.setText(currencyFormat.format(monthlyCustomPayment));	
	}
	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			customYear = progress;
			updateCustom();
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
		}
	};
	private TextWatcher amountEditTextWatcher = new TextWatcher()
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			try
			{
				purchaseAmount = Double.parseDouble(s.toString()) / 100.0;
			}
			catch (NumberFormatException e)
			{
				purchaseAmount = 0.0;
			}
			amountPurchasePrice.setText(currencyFormat.format(purchaseAmount));
			updateStandard();
			updateCustom();
		}
		@Override
		public void afterTextChanged(Editable s)
		{
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start,int count, int after)
		{
		}
	};
	private TextWatcher downPaymentEditTextWatcher = new TextWatcher()
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			try
			{
				downPayment = Double.parseDouble(s.toString()) / 100.0;
			}
			catch (NumberFormatException e)
			{
				downPayment = 0.0;
			}
			amountDownPayment.setText(currencyFormat.format(downPayment));
			updateStandard();
			updateCustom();
		}
		@Override
		public void afterTextChanged(Editable s)
		{
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start,int count, int after)
		{
		}
	};
	private TextWatcher interestEditTextWatcher = new TextWatcher()
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			try
			{
				interestRate = Double.parseDouble(s.toString())/10000;
			}
			catch (NumberFormatException e)
			{
				interestRate = 0.0;
			}
			amountInterestRate.setText(doubleFormat.format(interestRate*100));
			updateStandard();
			updateCustom();
		}
		@Override
		public void afterTextChanged(Editable s)
		{
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start,int count, int after)
		{
		}
	};
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
