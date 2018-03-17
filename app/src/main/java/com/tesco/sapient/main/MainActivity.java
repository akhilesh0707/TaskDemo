package com.tesco.sapient.main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.tesco.sapient.dto.ProductDto;
import com.tesco.sapient.dto.UserDTO;
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

    @Inject
    DataManager mDataManager;
    private ActivityComponent activityComponent;
    private UserDTO userDTO;

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

        Log.d(TAG, "UserDto" + userDTO.toString());
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.getItemTypes();
        presenter.getFilterItemTypes();
        presenter.getItems();
        presenter.getProductBarCodes();
    }

    public void init(UserDTO userDTO) {
        textViewTitle.setText(userDTO.getStoreName());
        textViewStoreId.setText(String.valueOf(userDTO.getStoreId()));
        textViewStoreId.setVisibility(View.VISIBLE);
    }

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

    @OnClick(R.id.buttonCancel)
    public void addItemCancelClick(View view) {
        buttonAddItem.setVisibility(View.VISIBLE);
        CollapseExpandAnimation.expandOrCollapse(addItemView, "collapse");
    }

    @OnClick(R.id.buttonSubmit)
    public void addItemClick(View view) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void fillAddItemSpinner(List<ItemTypeDTO> list) {
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, list);
        spinnerItemType.setAdapter(customSpinnerAdapter);
    }

    @Override
    public void noItemTypeRecord(List<ItemTypeDTO> list) {

    }

    @Override
    public void itemAddSuccessfullyMessage() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_add_successfully), Snackbar.LENGTH_LONG).show();
        resetAddItemForm();
        presenter.getItems();
    }

    @Override
    public void itemAddErrorMessage() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_add_fail), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void resetAddItemForm() {
        editTextProductCode.setText(null);
        spinnerItemType.setSelection(0);
        editTextAmountSingle.setText(null);
        editTextPrice.setText(null);
    }

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

    @Override
    public void noItemsErrorMessage() {
        textViewNoRecord.setVisibility(View.VISIBLE);
        recyclerViewItem.setVisibility(View.GONE);
    }

    @Override
    public void productBarCodeList(List<ProductDto> list) {
        final ProductAutocompleteAdapter adapter = new ProductAutocompleteAdapter(this, list);
        editTextProductCode.setThreshold(1);
        editTextProductCode.setAdapter(adapter);
        editTextProductCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDto productDto = adapter.getItem(position);
                editTextProductCode.setText(String.valueOf(productDto.getBarCode()));
                editTextPrice.setText(String.valueOf(productDto.getPrice()));
            }
        });
    }

    @Override
    public void noProductBarCodeErrorMessage() {

    }

    @Override
    public void itemDeleteSuccessfully() {
        presenter.getItems();
    }

    @Override
    public void itemDeleteError() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_delete_fail), Snackbar.LENGTH_LONG).show();
    }

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

    @Override
    public void fillFilterNoRecordError(List<ItemTypeDTO> list) {
        list.add(0, new ItemTypeDTO("All"));
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, list);
        spinnerFilter.setAdapter(customSpinnerAdapter);
    }

    @Override
    public void onItemRemoveClicked(ItemDTO itemDTO) {
        Log.d(TAG, "Delete item: " + itemDTO.getId());
        presenter.deleteItem(itemDTO);
    }


}
