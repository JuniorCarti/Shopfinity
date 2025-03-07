package fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.airbnb.lottie.LottieAnimationView;
import com.example.shopfinity.R;

public class OnboardingFragment extends Fragment {

    private int position;

    public OnboardingFragment() {
        // Required empty public constructor
    }

    public static OnboardingFragment newInstance(int position) {
        OnboardingFragment fragment = new OnboardingFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.onboarding_screen, container, false);

        // Initialize views
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottieAnimationView);
        TextView mainHeaderTextView = view.findViewById(R.id.mainHeaderTextView);
        TextView subtextTextView = view.findViewById(R.id.subtextTextView);

        // Update content based on position
        switch (position) {
            case 0:
                lottieAnimationView.setAnimation(R.raw.onboarding_animation1); // Lottie JSON file
                mainHeaderTextView.setText("Explore Our Products");
                subtextTextView.setText("Find Exercise Books, Drawing Books, Office Supplies and More");
                break;
            case 1:
                lottieAnimationView.setAnimation(R.raw.onboarding_animation2); // Lottie JSON file
                mainHeaderTextView.setText("Easy Ordering and Fast Delivery");
                subtextTextView.setText("Order and get your stationery delivered Fast and Secure");
                break;
            case 2:
                lottieAnimationView.setAnimation(R.raw.onboarding_animation3); // Lottie JSON file
                mainHeaderTextView.setText("Secure Payments And Reliable Service");
                subtextTextView.setText("Multiple Payment Options e.g Stripe, Paypal, Bank Cards");
                break;
        }

        // Play the animation
        lottieAnimationView.playAnimation();

        return view;
    }
}