package pl.rzeszow.wsiz.zhekabandit97.recipe4u.old;

//
//public class InfoFragment extends Fragment implements SearchRecipesContract.View{
//
//    private TextView plainTextInfo;
//    private FirstPresenter presenter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        presenter = new FirstPresenter();
//        View view = inflater.inflate(R.layout.fragment_info, container, false);
//        plainTextInfo = (TextView)view.findViewById(R.id.plainText);
//        sayHello();
//        return view;
//    }
//
//
//    @Override
//    public void sayHello() {
//        FoodApiInterfaceImpl dao = new FoodApiInterfaceImpl();
//        String [] prods = new String[] {"Onion","Tomato"};
//        Call<JSONObject> recipesByAllTitles = null;
//        try {
//            recipesByAllTitles = dao.getRecipesByAllTitles(prods);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Recipes: " + recipesByAllTitles);
////        Toast.makeText(getContext(), presenter.load(), Toast.LENGTH_LONG).show();
//    }
//}
