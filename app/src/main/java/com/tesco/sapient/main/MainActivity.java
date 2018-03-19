package com.tesco.sapient.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;
import com.tesco.sapient.MyApplication;
import com.tesco.sapient.R;
import com.tesco.sapient.custom.CustomSpinnerAdapter;
import com.tesco.sapient.custom.ProductAutocompleteAdapter;
import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.di.component.ActivityComponent;
import com.tesco.sapient.di.component.DaggerActivityComponent;
import com.tesco.sapient.di.module.ActivityModule;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDTO;
import com.tesco.sapient.dto.UserDTO;
import com.tesco.sapient.login.LoginActivity;
import com.tesco.sapient.main.adapter.ItemAdapter;
import com.tesco.sapient.main.adapter.OnItemClickListener;
import com.tesco.sapient.util.CollapseExpandAnimation;
import com.tesco.sapient.util.KeyboardUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MainActivity or UI
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class MainActivity extends AppCompatActivity implements MainActivityView, OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter presenter;
    private Context context;
    private Unbinder unbinder;
    private ItemAdapter itemAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.textViewStoreId)
    TextView textViewStoreId;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recyclerViewItem)
    RecyclerView recyclerViewItem;
    @BindView(R.id.textViewNoRecord)
    TextView textViewNoRecord;
    @BindView(R.id.spinnerFilter)
    Spinner spinnerFilter;

    // Add new Item fields
    @BindView(R.id.addItemView)
    View addItemView;
    @BindView(R.id.buttonAddItem)
    Button buttonAddItem;
    @BindView(R.id.editTextProductCode)
    AutoCompleteTextView editTextProductCode;
    @BindView(R.id.spinnerItemType)
    Spinner spinnerItemType;
    @BindView(R.id.editTextAmountSingle)
    EditText editTextAmountSingle;
    @BindView(R.id.editTextPrice)
    EditText editTextPrice;
    @BindView(R.id.buttonSubmit)
    Button buttonSubmit;

    @Inject
    DataManager mDataManager;
    private ActivityComponent activityComponent;
    private UserDTO userDTO;

    /**
     * Get component for dependency injection - dagger
     *
     * @return ActivityComponent
     */
    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MyApplication.get(this).getComponent())
                    .build();
        }
        this.userDTO = MyApplication.get(this).getUser();
        init(userDTO);
        return activityComponent;
    }

    /**
     * LifeCycle method to handle ui and other elements
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initializing ButterKnife
        unbinder = ButterKnife.bind(this);
        // Initializing login activity context
        context = this;
        //Initialize DataBase
        getActivityComponent().inject(this);
        // Initializing Presenter
        presenter = new MainActivityPresenter(this, mDataManager);
    }

    /**
     * OnResume Lifecycle method of activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        presenter.getItemTypes();
        presenter.getFilterItemTypes();
        presenter.getItems();
        presenter.getProductBarCodes();
    }

    /**
     * Set and init data to toolbar and other settings
     *
     * @param UserDTO
     */
    public void init(UserDTO userDTO) {
        textViewTitle.setText(userDTO.getStoreName());
        textViewStoreId.setText(String.valueOf(userDTO.getStoreId()));
        textViewStoreId.setVisibility(View.VISIBLE);

        editTextProductCode.addTextChangedListener(watcher);
        editTextAmountSingle.addTextChangedListener(watcher);
        editTextPrice.addTextChangedListener(watcher);
        buttonSubmit.setEnabled(false);

        // Toolbar and title settings
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Button hide show clicked
     *
     * @param view
     */
    @OnClick(R.id.buttonAddItem)
    public void showAddItemClick(View view) {
        if (addItemView.isShown()) {
            buttonAddItem.setVisibility(View.VISIBLE);
            CollapseExpandAnimation.expandOrCollapse(addItemView, "collapse");
        } else {
            buttonAddItem.setVisibility(View.GONE);
            CollapseExpandAnimation.expandOrCollapse(addItemView, "expand");
        }
    }

    /**
     * Add new item View cancel button click
     *
     * @param view
     */
    @OnClick(R.id.buttonCancel)
    public void addItemCancelClick(View view) {
        buttonAddItem.setVisibility(View.VISIBLE);
        CollapseExpandAnimation.expandOrCollapse(addItemView, "collapse");
    }

    /**
     * Add item to database click
     *
     * @param view
     */
    @OnClick(R.id.buttonSubmit)
    public void addItemClick(View view) {
        Log.d(TAG, "Add item clicked");
        KeyboardUtil.hideSoftKeyboard(context);
        int barCode = Integer.parseInt(editTextProductCode.getText().toString().trim());
        ItemTypeDTO itemTypeDTO = (ItemTypeDTO) spinnerItemType.getSelectedItem();
        int quantity = Integer.parseInt(editTextAmountSingle.getText().toString().trim());
        double price = Double.parseDouble(editTextPrice.getText().toString().trim());

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemBarCode(barCode);
        itemDTO.setItemTypeCode(itemTypeDTO.getId());
        itemDTO.setItemQuantity(quantity);
        itemDTO.setItemPrice(price);

        Log.d(TAG, itemDTO.toString());
        presenter.addItem(itemDTO);
    }

    /**
     * OnDestroy method of activity to dispose objects
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter = null;
    }

    /**
     * the menu layout has the 'add/new' menu item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * On menu item click handler
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.menuLogout:
                startActivity(new Intent(context, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * Fill the items to spinner
     *
     * @param list
     */
    @Override
    public void fillAddItemSpinner(List<ItemTypeDTO> list) {
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, list);
        spinnerItemType.setAdapter(customSpinnerAdapter);
    }

    /**
     * No Item type record found in database
     *
     * @param list
     */
    @Override
    public void noItemTypeRecord(List<ItemTypeDTO> list) {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_no_item_type), Snackbar.LENGTH_LONG).show();
    }

    /**
     * Method used to show item is added successfully to database
     */
    @Override
    public void itemAddSuccessfullyMessage() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_add_successfully), Snackbar.LENGTH_LONG).show();
        resetAddItemForm();
        presenter.getItems();
    }

    /**
     * Method used to show error message when their is error in insertion of item
     */
    @Override
    public void itemAddErrorMessage() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_add_fail), Snackbar.LENGTH_LONG).show();
    }

    /**
     * Method used to reset add item form after adding item to database
     */
    @Override
    public void resetAddItemForm() {
        editTextProductCode.setText(null);
        spinnerItemType.setSelection(0);
        editTextAmountSingle.setText(null);
        editTextPrice.setText(null);
    }

    /**
     * Method used to show items to list on UI
     *
     * @param itemDTOList
     */
    @Override
    public void showItems(List<ItemDTO> itemDTOList) {
        textViewNoRecord.setVisibility(View.GONE);
        recyclerViewItem.setVisibility(View.VISIBLE);
        itemAdapter = new ItemAdapter(itemDTOList);
        itemAdapter.setClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewItem.setLayoutManager(mLayoutManager);
        ((ItemAdapter) itemAdapter).setMode(Attributes.Mode.Single);
        recyclerViewItem.setAdapter(itemAdapter);
    }

    /**
     * Method used to show error message it no item record found in database
     */
    @Override
    public void noItemsErrorMessage() {
        textViewNoRecord.setVisibility(View.VISIBLE);
        recyclerViewItem.setVisibility(View.GONE);
    }

    /**
     * Method to show product in AutoComplete
     *
     * @param list
     */
    @Override
    public void productBarCodeList(List<ProductDTO> list) {
        final ProductAutocompleteAdapter adapter = new ProductAutocompleteAdapter(this, list);
        editTextProductCode.setThreshold(1);
        editTextProductCode.setAdapter(adapter);
        editTextProductCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDTO productDTO = adapter.getItem(position);
                editTextProductCode.setText(String.valueOf(productDTO.getBarCode()));
                editTextPrice.setText(String.valueOf(productDTO.getPrice()));
            }
        });
    }

    /**
     * Method is used to show error message is no product barcode in database
     */
    @Override
    public void noProductBarCodeErrorMessage() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_no_product_barcode), Snackbar.LENGTH_LONG).show();
    }

    /**
     * Method used to show delete message if record is deleted from database
     */
    @Override
    public void itemDeleteSuccessfully() {
        presenter.getItems();
    }

    /**
     * Method is used to show delete error message is record is not deleted from database
     */
    @Override
    public void itemDeleteError() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_delete_fail), Snackbar.LENGTH_LONG).show();
    }

    /**
     * Method is used to add record to filter spinner
     *
     * @param list
     */
    @Override
    public void fillFilterSpinner(List<ItemTypeDTO> list) {
        list.add(0, new ItemTypeDTO("All"));
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, list);
        spinnerFilter.setAdapter(customSpinnerAdapter);
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemTypeDTO itemTypeDTO = (ItemTypeDTO) spinnerFilter.getSelectedItem();
                if (itemTypeDTO != null) {
                    Log.i(TAG, String.valueOf(itemTypeDTO.getId()));
                    if (itemAdapter != null) {
                        itemAdapter.getFilter().filter(String.valueOf(itemTypeDTO.getId()));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Method is used to show error message is filter record is not found in database
     *
     * @param list
     */
    @Override
    public void fillFilterNoRecordError(List<ItemTypeDTO> list) {
        list.add(0, new ItemTypeDTO("All"));
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, list);
        spinnerFilter.setAdapter(customSpinnerAdapter);
    }

    /**
     * Method is used to remove item from database and perform presenter delete method
     *
     * @param itemDTO
     */
    @Override
    public void onItemRemoveClicked(ItemDTO itemDTO) {
        Log.d(TAG, "Delete item: " + itemDTO.getId());
        presenter.deleteItem(itemDTO);
    }

    /**
     * Watcher class is used to enable or disable submit button
     */
    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String barCode = editTextProductCode.getText().toString().trim();
            String quantity = editTextAmountSingle.getText().toString().trim();
            String price = editTextPrice.getText().toString().trim();
            if (barCode.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
                buttonSubmit.setEnabled(false);
            } else {
                buttonSubmit.setEnabled(true);
            }
        }
    };
}
